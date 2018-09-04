package com.nostratech.project.persistence.vo;

import lombok.Data;

/**
 * Created by yukibuwana on 1/24/17.
 */

@Data
public class ResultVO {

    private String message;
    private Object result;

    public ResultVO() {
    }

    public ResultVO(String message, Object result) {
        this.message = message;
        this.result = result;
    }
}
