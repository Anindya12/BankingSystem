package com.nostratech.project.controller;

import com.nostratech.project.persistence.service.TransactionService;
import com.nostratech.project.persistence.vo.ResultPageVO;
import com.nostratech.project.persistence.vo.ResultVO;
import com.nostratech.project.persistence.vo.TransactionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @RequestMapping(value = "/deposit",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ResultVO> deposit(@RequestBody TransactionVO transactionVO) {
        AbstractRequestHandler handler = new AbstractRequestHandler() {
            @Override
            public Object processRequest() {
                return transactionService.savedTransaction(transactionVO);
            }
        };
        return handler.getResult();
    }

    @RequestMapping(value = "/withdraw",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ResultVO> withdraw(@RequestBody TransactionVO transactionVO) {
        AbstractRequestHandler handler = new AbstractRequestHandler() {
            @Override
            public Object processRequest() {
                return transactionService.withDraw(transactionVO);
            }
        };
        return handler.getResult();
    }

    @RequestMapping(value = "/listTransaction",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ResultPageVO> page(@RequestParam(value = "acountId", required = false) String acountId,
                                             @RequestParam(value = "page", defaultValue = "0") Integer page,
                                             @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                             @RequestParam(value = "sortBy", required = false) String sortBy,
                                             @RequestParam(value = "direction", required = false) String direction
    ) {
        Map<String, Object> pageMap = transactionService.historyTransaction(acountId, page, limit, sortBy, direction);
        return AbstractRequestHandler.constructListResult(pageMap);
    }

}
