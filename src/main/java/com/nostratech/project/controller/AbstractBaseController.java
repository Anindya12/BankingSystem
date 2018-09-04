package com.nostratech.project.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.nostratech.project.enums.StatusCode;
import com.nostratech.project.persistence.domain.Base;
import com.nostratech.project.persistence.service.BaseService;
import com.nostratech.project.persistence.vo.BaseVO;
import com.nostratech.project.persistence.vo.ResultPageVO;
import com.nostratech.project.persistence.vo.ResultVO;
import com.nostratech.project.util.RestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by yukibuwana on 1/24/17.
 */

public abstract class AbstractBaseController<T extends Base, V extends BaseVO, Z> implements RestController<Z, ResultVO> {

    private static final Logger log = LoggerFactory.getLogger(AbstractBaseController.class);

    protected abstract BaseService<T, V, Z> getDomainService();

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResultVO> handleAllExceptions(Exception ex) {
        log.error("ERROR", ex);

        String message = ex.getMessage();
        if (ex.getCause() instanceof InvalidFormatException) {
            message = "Invalid value: " + ((InvalidFormatException) ex.getCause()).getValue();
        }

        return RestUtil.getJsonResponse(new ResultVO(StatusCode.ERROR.name(), message));
    }

    @Override
    public ResponseEntity<ResultVO> add(@RequestBody Z voInput) {
        return RestUtil.getJsonResponse(null, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    public ResponseEntity<ResultVO> edit(@PathVariable("id") String secureId, @RequestBody Z voInput) {
        return RestUtil.getJsonResponse(null, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    public ResponseEntity<ResultVO> delete(@PathVariable("id") String secureId) {
        return RestUtil.getJsonResponse(null, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    public ResponseEntity<ResultVO> findById(@PathVariable("id") String secureId) {
        return RestUtil.getJsonResponse(null, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    public ResponseEntity<ResultPageVO> page(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                             @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                             @RequestParam(value = "sortBy", required = false) String sortBy,
                                             @RequestParam(value = "direction", required = false) String direction,
                                             @RequestParam(value = "searchBy", required = false) String searchBy,
                                             @RequestParam(value = "searchVal", required = false) String searchVal) {
        return RestUtil.getJsonResponse(null, HttpStatus.METHOD_NOT_ALLOWED);
    }

    protected ResponseEntity<ResultPageVO> constructListResult(Map<String, Object> pageMap) {
        return AbstractRequestHandler.constructListResult(pageMap);
    }

    @Override
    public ResponseEntity<ResultVO> list(@RequestParam(value = "sortBy", required = false) String sortBy,
                                         @RequestParam(value = "direction", required = false) String direction,
                                         @RequestParam(value = "searchBy", required = false) String searchBy,
                                         @RequestParam(value = "searchVal", required = false) String searchVal) {
        return RestUtil.getJsonResponse(null, HttpStatus.METHOD_NOT_ALLOWED);
    }

}
