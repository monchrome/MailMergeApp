package com.mborg.mailMerge.error.handlers;

import com.mborg.mailMerge.config.ErrorSettings;
import com.mborg.mailMerge.error.ErrorDetails;
import com.mborg.mailMerge.error.exceptions.InvalidDataException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Set;

/*
    @author monChrome
 */
@ControllerAdvice
public class MailMergeServiceExceptionHandler extends ResponseEntityExceptionHandler {

    private ErrorSettings errorSettings;

    @Autowired
    public MailMergeServiceExceptionHandler(ErrorSettings errorSettings) {
        this.errorSettings = errorSettings;
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object>
    handleAllExceptions(Exception ex, WebRequest request){
        NativeWebRequest nativeRequest = (NativeWebRequest) request;
        HttpServletRequest servletRequest = nativeRequest.getNativeRequest(HttpServletRequest.class);
        return getErrorResponse(ex, servletRequest, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
    @ExceptionHandler(value = InvalidDataException.class)
    public ResponseEntity<Object> invalidDataExceptionHandler(InvalidDataException ex,
                                                              HttpServletRequest request) {
        return getErrorResponse(ex, request, HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<Object> constraintViolationExceptionHandler(ConstraintViolationException ex,
                                                                      HttpServletRequest request) {
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder();
        for (ConstraintViolation<?> violation : violations) {
            strBuilder.append(violation.getMessage() + "Violating value = "
                    + violation.getInvalidValue());
        }
        return getErrorResponse(ex, request, HttpStatus.BAD_REQUEST, strBuilder.toString());
    }

    private ResponseEntity<Object> getErrorResponse(Exception ex,
                                                          HttpServletRequest request,
                                                          HttpStatus httpStatus,
                                                          String errorMessage) {
        String exceptionMsg = errorMessage != null ? errorMessage : ex.getMessage();

        ErrorDetails errorDetails = ErrorDetails.builder().timeStamp(LocalDateTime.now())
                .httpStatus(httpStatus.toString())
                .errorMessage(exceptionMsg)
                .requestURI(request.getRequestURI())
                .methodName(request.getMethod())
                .exception(includeStackTrace(ex.fillInStackTrace())).build();

        return new ResponseEntity<>(errorDetails, httpStatus);
    }

    private String includeStackTrace(Throwable ex) {
        return errorSettings.isIncludeStackTrace() ? ex.fillInStackTrace().toString() : null;
    }
}
