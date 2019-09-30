package com.myretail.rest.aggregator.impl;

import com.myretail.exceptions.ProductException;
import com.myretail.respository.entity.Price;
import com.myretail.rest.aggregator.ProductAggregator;
import com.myretail.rest.product.response.ProductResponse;
import com.myretail.service.item.ItemService;
import com.myretail.service.price.PriceService;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.reactivex.Observable;

@Service
public class ProductAggregatorImpl implements ProductAggregator {

    private PriceService priceService;

    private ItemService itemService;

    @Autowired
    public ProductAggregatorImpl(ItemService itemService, PriceService priceService) {
        this.itemService = itemService;
        this.priceService  = priceService;
    }

    @Override
    public ProductResponse fetchProduct(String productId) throws ProductException {
        Observable<ProductResponse> itemObservable
                = Observable.fromCallable(() -> itemService.getProductDetails(productId))
                        .subscribeOn(Schedulers.io()).observeOn(Schedulers.single());
        Observable<Price> priceObservable =
                Observable.fromCallable(() -> priceService.fetchPrice(productId)).subscribeOn(
                        Schedulers.io()).observeOn(Schedulers.single())
                            .onErrorReturnItem(new Price());

        ProductResponse product = Observable.zip(itemObservable, priceObservable, this::aggregate)
                                            .blockingFirst();

        return product;
    }

    public ProductResponse aggregate(ProductResponse product, Price price) {
        if (price != null) {
            ProductResponse.Price priceResponse = new ProductResponse.Price();
            priceResponse.setValue(price.getPrice());
            priceResponse.setCurrency(price.getCurrency());
            product.setCurrentPrice(priceResponse);
        }

        return product;
    }
}
