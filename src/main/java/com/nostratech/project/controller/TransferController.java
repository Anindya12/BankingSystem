package com.nostratech.project.controller;

import com.nostratech.project.persistence.service.TransferService;
import com.nostratech.project.persistence.vo.ResultPageVO;
import com.nostratech.project.persistence.vo.ResultVO;
import com.nostratech.project.persistence.vo.TransferVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/transaction")
public class TransferController {

    @Autowired
    TransferService transferService;

    @RequestMapping(value = "/transfer",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ResultVO> deposit(@RequestBody TransferVO transferVO) {
        AbstractRequestHandler handler = new AbstractRequestHandler() {
            @Override
            public Object processRequest() {
                return transferService.transfer(transferVO);
            }
        };
        return handler.getResult();
    }

    @RequestMapping(value = "/listTransfer",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ResultPageVO> page(@RequestParam(value = "acountIdSource", required = false) String acountIdSource,
                                             @RequestParam(value = "acountIdDest", required = false) String acountIdDest,
                                             @RequestParam(value = "page", defaultValue = "0") Integer page,
                                             @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                             @RequestParam(value = "sortBy", required = false) String sortBy,
                                             @RequestParam(value = "direction", required = false) String direction
    ) {
        Map<String, Object> pageMap = transferService.historyTransfer(acountIdSource, acountIdDest, page, limit, sortBy, direction);
        return AbstractRequestHandler.constructListResult(pageMap);
    }
}
