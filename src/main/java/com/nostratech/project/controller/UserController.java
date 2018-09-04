package com.nostratech.project.controller;

import com.nostratech.project.persistence.service.UserService;
import com.nostratech.project.persistence.vo.ResultPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/listUser",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ResultPageVO> page(@RequestParam(value = "fullName", required = false) String fullName,
                                             @RequestParam(value = "username", required = false) String username,
                                             @RequestParam(value = "accountId", required = false) String accountId,
                                             @RequestParam(value = "page", defaultValue = "0") Integer page,
                                             @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                             @RequestParam(value = "sortBy", required = false) String sortBy,
                                             @RequestParam(value = "direction", required = false) String direction
    ) {
        Map<String, Object> pageMap = userService.searchUser(fullName, username, accountId, page, limit, sortBy, direction);
        return AbstractRequestHandler.constructListResult(pageMap);
    }
}
