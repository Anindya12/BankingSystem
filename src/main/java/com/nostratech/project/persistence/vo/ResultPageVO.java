package com.nostratech.project.persistence.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Created by yukibuwana on 1/24/17.
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultPageVO extends ResultVO {

    private String pages;
    private String elements;
}
