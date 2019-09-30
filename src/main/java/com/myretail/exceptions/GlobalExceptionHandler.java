package com.myretail.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.PropertyResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.lang.annotation.Annotation;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class GlobalExceptionHandler {

    PropertyResolver propertyResolver;

    @Autowired
    public GlobalExceptionHandler(PropertyResolver propertyResolver) {
        this.propertyResolver = propertyResolver;
    }

    @ExceptionHandler({ProductException.class})
    public ResponseEntity<APIError> handleGenericApplicationError(ProductException exception) {
        APIError apiError = new APIError(exception.getErrorKey(), exception.getErrorMessage());
        return new ResponseEntity<>(apiError, getHttpStatus(exception));
    }

    protected HttpStatus getHttpStatus(Exception e) {
        HttpStatus httpStatus;
        Annotation statusAnnotation
                = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class);

        if (statusAnnotation != null) {
            httpStatus = (HttpStatus) AnnotationUtils.getValue(statusAnnotation);
        } else {
            httpStatus = NOT_FOUND;
        }
        return httpStatus;
    }

}
