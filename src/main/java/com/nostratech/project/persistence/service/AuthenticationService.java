package com.nostratech.project.persistence.service;

import com.nostratech.project.converter.AccountVOConverter;
import com.nostratech.project.converter.UserVOConverter;
import com.nostratech.project.exception.NostraException;
import com.nostratech.project.persistence.domain.Account;
import com.nostratech.project.persistence.domain.Transaction;
import com.nostratech.project.persistence.domain.User;
import com.nostratech.project.persistence.repository.AccountRepository;
import com.nostratech.project.persistence.repository.TransactionRepository;
import com.nostratech.project.persistence.repository.UserRepository;
import com.nostratech.project.persistence.vo.AccountVO;
import com.nostratech.project.persistence.vo.AuthenticationVO;
import com.nostratech.project.persistence.vo.LoginVO;
import com.nostratech.project.persistence.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserVOConverter userVOConverter;

    @Autowired
    AccountVOConverter accountVOConverter;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;
            ;
    @Transactional
    public AuthenticationVO registratrion(UserVO userVO) {

        User user = userRepository.findByUsername(userVO.getUsername());
        if (user != null) {
            if (user.getUsername().equalsIgnoreCase(userVO.getUsername()))
                throw new NostraException("Username is already exist");

        }

        final Account[] saveAccount = {new Account()};
        User userAdded = userVOConverter.transferVOToModel(userVO,null);
                userVO.getAccountId().forEach(accountVO ->{
        //userVO.getAccountId().longValue(accountVO->{
               Account accountAdded = accountVOConverter.transferVOToModel(accountVO,null);
               accountAdded.setBalance(0.0);
               saveAccount[0] = accountRepository.save(accountAdded);

               Transaction transactionAdded = new Transaction();
               transactionAdded.setBalance(accountVO.getBalance());
               transactionAdded.setAccountId(saveAccount[0]);
               transactionRepository.save(transactionAdded);


                });

        userAdded.setAccountId(saveAccount[0]);
        User saved = userRepository.save(userAdded);

        AuthenticationVO response = new AuthenticationVO();
        response.setAccountId(saved.getAccountId().getSecureId());


        return response;

    }

    public LoginVO login(LoginVO loginVO) {
        User username = userRepository.findByUsername2(loginVO.getUsername());
        User password = userRepository.findByPassword(loginVO.getPassword());

        if(username != null && password != null){
            return loginVO;
        }else{
            throw new NostraException("Username is not valid");
        }
    }
}
