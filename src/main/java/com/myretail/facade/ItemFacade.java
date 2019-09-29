package com.myretail.facade;

import com.myretail.rest.product.response.ItemResponse;

public interface ItemFacade {
    ItemResponse getItem(String productId);
}
