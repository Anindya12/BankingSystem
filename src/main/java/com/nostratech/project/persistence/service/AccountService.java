package com.nostratech.project.persistence.service;


import com.nostratech.project.converter.AccountVOConverter;
import com.nostratech.project.persistence.domain.Account;
import com.nostratech.project.persistence.domain.Transaction;
import com.nostratech.project.persistence.repository.AccountRepository;
import com.nostratech.project.persistence.vo.AccountVO;
import com.nostratech.project.persistence.vo.TransactionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountVOConverter accountVOConverter;

    public Map<String, Object> searchAccount (String accountId, Integer page, Integer limit, String sortBy, String direction) {

        Page<Account> resultPage = null;
        List<Account> models;
        Collection<AccountVO> vos;

        sortBy = StringUtils.isEmpty(sortBy) ? "secureId" : sortBy;
        direction = StringUtils.isEmpty(direction) ? "desc" : direction;
        Pageable pageable = new PageRequest(page, limit, AbstractBaseService.getSortBy(sortBy, direction));

        if(!StringUtils.isEmpty(accountId)){
            resultPage = accountRepository.findBySecureId(accountId,pageable);
        }else{
            resultPage = accountRepository.findAll(pageable);
        }

        models = resultPage.getContent();
        vos = accountVOConverter.transferListOfModelToListOfVO(models, new ArrayList<>());
        return AbstractBaseService.constructMapReturn(vos, resultPage.getTotalElements(), resultPage.getTotalPages());

    }

}
