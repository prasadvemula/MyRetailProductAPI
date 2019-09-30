package com.myretail.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Data
public class ProductException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String errorKey;
    private String errorMessage;

    public ProductException(String errorKey) {
        super(errorKey);
        this.errorKey = errorKey;
    }

    public ProductException(String errorKey, Throwable e) {
        super(errorKey, e);
        this.errorKey = errorKey;
    }
}
