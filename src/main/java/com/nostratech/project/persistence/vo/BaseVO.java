package com.nostratech.project.persistence.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by yukibuwana on 1/24/17.
 */

@Data
public class BaseVO implements Serializable {

    /**
     * Secure ID / UUID
     */
    private String id;
    private Integer version;
}
