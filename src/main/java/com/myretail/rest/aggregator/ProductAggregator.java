package com.myretail.rest.aggregator;

import com.myretail.rest.product.response.ProductResponse;

public interface ProductAggregator {
    ProductResponse fetchProduct(String productId);
}
