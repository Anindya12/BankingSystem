package com.nostratech.project.persistence.service;

import com.nostratech.project.converter.TransactionVOConverter;
import com.nostratech.project.exception.NostraException;
import com.nostratech.project.persistence.domain.Account;
import com.nostratech.project.persistence.domain.Transaction;
import com.nostratech.project.persistence.repository.AccountRepository;
import com.nostratech.project.persistence.repository.TransactionRepository;
import com.nostratech.project.persistence.vo.TransactionVO;
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
public class TransactionService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionVOConverter transactionVOConverter;

    @Autowired
    TransactionRepository transactionRepository;

    @Transactional
    public TransactionVO savedTransaction(TransactionVO transactionVO) {

        Account account = accountRepository.findBySecureId(transactionVO.getAccountId());
        if(account == null) {
            throw new NostraException("Account not found");
        }


        Transaction transactionSaved = transactionVOConverter.transferVOToModel(transactionVO, null);
        transactionSaved.setAccountId(account);
        transactionSaved.setStatus("S");
        Transaction saved = transactionRepository.save(transactionSaved);

        Double balance = transactionSaved.getBalance() + account.getBalance();
        account.setBalance(balance);
        accountRepository.save(account);


        return transactionVO;

    }

    @Transactional
    public TransactionVO withDraw(TransactionVO transactionVO) {

        Account account = accountRepository.findBySecureId(transactionVO.getAccountId());
        if(account == null) {
            throw new NostraException("Account not found");
        }

        if(account.getBalance() == 0 || account.getBalance() < transactionVO.getBalance() ){
            throw new NostraException("Your balance is insufficient");
        }


        Transaction transactionWithDraw = transactionVOConverter.transferVOToModel(transactionVO, null);
        transactionWithDraw.setAccountId(account);
        transactionWithDraw.setStatus("W");
        Transaction saved = transactionRepository.save(transactionWithDraw);

        Double balance = transactionWithDraw.getBalance() - account.getBalance();
        account.setBalance(balance);
        accountRepository.save(account);

        return transactionVO;

    }

    public Map<String, Object> historyTransaction(String accountId, Integer page, Integer limit, String sortBy, String direction) {

        Page<Transaction> resultPage = null;
        List<Transaction> models;
        Collection<TransactionVO> vos;

        sortBy = StringUtils.isEmpty(sortBy) ? "accountId" : sortBy;
        direction = StringUtils.isEmpty(direction) ? "desc" : direction;
        Pageable pageable = new PageRequest(page, limit, AbstractBaseService.getSortBy(sortBy, direction));

        if(!StringUtils.isEmpty(accountId)){
            resultPage = transactionRepository.findByAccountId(accountId,pageable);
        }else{
            resultPage = transactionRepository.findAll(pageable);
        }

        models = resultPage.getContent();
        vos = transactionVOConverter.transferListOfModelToListOfVO(models, new ArrayList<>());
        return AbstractBaseService.constructMapReturn(vos, resultPage.getTotalElements(), resultPage.getTotalPages());

    }
}
