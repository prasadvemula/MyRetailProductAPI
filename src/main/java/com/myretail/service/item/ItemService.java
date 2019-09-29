package com.myretail.service.item;

import com.myretail.rest.product.response.ProductResponse;

public interface ItemService {
    ProductResponse getProductDetails(String productId);
}
