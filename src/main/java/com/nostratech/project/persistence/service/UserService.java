package com.nostratech.project.persistence.service;

import com.nostratech.project.converter.UserVOConverter;
import com.nostratech.project.persistence.domain.User;
import com.nostratech.project.persistence.repository.UserRepository;
import com.nostratech.project.persistence.vo.UserVO;
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
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserVOConverter userVOConverter;

    public Map<String, Object> searchUser(String fullName, String username, String accountId, Integer page, Integer limit, String sortBy, String direction) {

        Page<User> resultPage = null;
        List<User> models;
        Collection<UserVO> vos;

        sortBy = StringUtils.isEmpty(sortBy) ? "fullName" : sortBy;
        direction = StringUtils.isEmpty(direction) ? "desc" : direction;
        Pageable pageable = new PageRequest(page, limit, AbstractBaseService.getSortBy(sortBy, direction));

        if(!StringUtils.isEmpty(fullName) && StringUtils.isEmpty(username) && StringUtils.isEmpty(accountId)){
            resultPage = userRepository.findByFullName(fullName.toLowerCase(),pageable);
        }else if(StringUtils.isEmpty(fullName) && !StringUtils.isEmpty(username) && StringUtils.isEmpty(accountId)) {
            resultPage = userRepository.findByUsername(username, pageable);
        }else if(StringUtils.isEmpty(fullName) && StringUtils.isEmpty(username) && !StringUtils.isEmpty(accountId)) {
            resultPage = userRepository.findByAccountId(accountId, pageable);
        }else {
            resultPage = userRepository.findAll(pageable);
        }

        models = resultPage.getContent();
        vos = userVOConverter.transferListOfModelToListOfVO(models, new ArrayList<>());
        return AbstractBaseService.constructMapReturn(vos, resultPage.getTotalElements(), resultPage.getTotalPages());

    }
}
