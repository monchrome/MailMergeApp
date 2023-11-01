package com.mborg.mailMerge.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/*
    @author monChrome
 */
@Builder
@Value
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDetails implements Serializable {
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    LocalDateTime timeStamp;
    String httpStatus;
    String errorMessage;
    String requestURI;
    String methodName;
    String exception;
}
