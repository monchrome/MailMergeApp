package com.mborg.mailMerge.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/*
    @author monChrome
 */
@Component
@ConfigurationProperties(prefix = "error.settings")
@Setter
@Getter
public class ErrorSettings {
    private boolean includeStackTrace;
}
