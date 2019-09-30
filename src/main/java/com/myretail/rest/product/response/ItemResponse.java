package com.myretail.rest.product.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemResponse {
    private Product product;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Product {
        private Item item;
    }

    @Data
    public static class Item{
        private String tcin;
        @JsonProperty("product_description")
        private ProductDescription productDescription;
    }

    @Data
    public static class ProductDescription {
        private String title;
    }

}




