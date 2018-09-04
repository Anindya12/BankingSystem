package com.nostratech.project.persistence.service;

import com.nostratech.project.converter.TransferVOConverter;
import com.nostratech.project.exception.NostraException;
import com.nostratech.project.persistence.domain.Account;
import com.nostratech.project.persistence.domain.Transfer;
import com.nostratech.project.persistence.domain.User;
import com.nostratech.project.persistence.repository.AccountRepository;
import com.nostratech.project.persistence.repository.TransferRepository;
import com.nostratech.project.persistence.vo.TransferVO;
import com.nostratech.project.persistence.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class TransferService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransferVOConverter transferVOConverter;

    @Autowired
    TransferRepository transferRepository;

    @Transactional
    public TransferVO transfer(TransferVO transferVO) {

        Account accountSource = accountRepository.findBySecureId(transferVO.getAccountIdSource());
        if(accountSource == null) {
            throw new NostraException("Account not found");
        }


        Account accountDest = accountRepository.findBySecureId(transferVO.getAccountIdDest());
        if(accountDest == null) {
            throw new NostraException("Account not found");
        }

        if(accountSource.getBalance() == 0 || accountSource.getBalance() < transferVO.getBalance() ){
            throw new NostraException("Your balance is insufficient");
        }


        Transfer transfer = transferVOConverter.transferVOToModel(transferVO, null);
        transfer.setAccountIdSource(accountSource);
        transfer.setAccountIdDest(accountDest);
        Transfer saved = transferRepository.save(transfer);

        Double balanceSource = accountSource.getBalance() - transfer.getBalance();
        accountSource.setBalance(balanceSource);
        accountRepository.save(accountSource);

        Double balanceDest = accountDest.getBalance() + transfer.getBalance();
        accountDest.setBalance(balanceDest);
        accountRepository.save(accountDest);


        return transferVO;

    }

    public Map<String, Object> historyTransfer(String accountIdSource, String accountIdDest, Integer page, Integer limit, String sortBy, String direction) {

        Page<Transfer> resultPage = null;
        List<Transfer> models;
        Collection<TransferVO> vos;

        sortBy = StringUtils.isEmpty(sortBy) ? "accountIdSource" : sortBy;
        direction = StringUtils.isEmpty(direction) ? "desc" : direction;
        Pageable pageable = new PageRequest(page, limit, AbstractBaseService.getSortBy(sortBy, direction));

        if(!StringUtils.isEmpty(accountIdSource) && StringUtils.isEmpty(accountIdDest)){
            resultPage = transferRepository.findByAccountIdSource(accountIdSource,pageable);
        }else if(StringUtils.isEmpty(accountIdSource) && !StringUtils.isEmpty(accountIdDest)){
            resultPage = transferRepository.findByAccountIdDest(accountIdDest,pageable);
        }else if(!StringUtils.isEmpty(accountIdSource) && !StringUtils.isEmpty(accountIdDest)) {
            resultPage = transferRepository.findByAccountIdSourceAndAccountIdDest(accountIdSource, accountIdDest, pageable);
        }else{
            resultPage = transferRepository.findAll(pageable);
        }

        models = resultPage.getContent();
        vos = transferVOConverter.transferListOfModelToListOfVO(models, new ArrayList<>());
        return AbstractBaseService.constructMapReturn(vos, resultPage.getTotalElements(), resultPage.getTotalPages());

    }
}
