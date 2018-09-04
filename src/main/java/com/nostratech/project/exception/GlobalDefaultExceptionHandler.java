package com.nostratech.project.exception;

import com.nostratech.project.enums.StatusCode;
import com.nostratech.project.persistence.vo.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yukibuwana on 1/25/17.
 */

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    @ExceptionHandler(value = {Exception.class, RuntimeException.class })
    public ResponseEntity<?> defaultErrorHandler(HttpServletRequest req, Exception e) {

        log.error(String.format("%s : Caught in Global Exception Handler for req: %s",
                e.getLocalizedMessage(), req.getRequestURL()));

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ResultVO restResponseVO = new ResultVO();
        restResponseVO.setResult(e.getMessage());
        restResponseVO.setMessage(StatusCode.ERROR.name());

        return new ResponseEntity<>(restResponseVO, status);
    }
}
