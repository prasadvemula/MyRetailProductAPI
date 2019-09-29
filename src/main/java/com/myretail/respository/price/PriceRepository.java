package com.myretail.respository.price;

import com.myretail.respository.entity.Price;

public interface PriceRepository {
    Price fetchPrice(String productId);
    Price createPrice(Price price);
    Price updatePrice(Price price);
}
