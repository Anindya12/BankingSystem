package com.nostratech.project.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

/**
 * Created by yukibuwana on 1/26/17.
 */

@Configuration
public class UsernameAuditorAware implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {
        return "unknown";
    }
}
