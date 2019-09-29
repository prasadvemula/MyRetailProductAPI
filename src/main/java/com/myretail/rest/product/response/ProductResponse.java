package com.myretail.rest.product.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse {
    private String productId;

    private String title;

    private Price currentPrice;

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Price {
        private String value;
        private String currency;
    }
}
