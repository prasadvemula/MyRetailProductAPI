package com.myretail.facade.impl;

import com.myretail.config.ApiConfig;
import com.myretail.exceptions.ProductException;
import com.myretail.facade.ItemFacade;
import com.myretail.rest.product.response.ItemResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Slf4j
public class ItemFacadeImpl implements ItemFacade {

    private RestTemplate itemRestTemplate;

    private ApiConfig apiConfig;

    private static String itemQueryString
            = "?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews," +
            "rating_and_review_statistics,question_answer_statistics";

    @Autowired
    public ItemFacadeImpl(RestTemplateBuilder builder, ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
        this.itemRestTemplate = builder.rootUri(apiConfig.getItemApiBaseURL())
                .setConnectTimeout(apiConfig.getItemApiConnectionTimeout())
                .setReadTimeout(apiConfig.getItemApiReadTimeout())
                .build();
    }

    @Override
    @HystrixCommand(defaultFallback = "callGetItemFallback")
    public ItemResponse getItem(String productId) throws ProductException {
        StopWatch watch = new StopWatch();
        watch.start();
        ItemResponse response;
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(apiConfig.getItemApiBaseURL()
                        .concat("/pdp/tcin/").concat(productId)
                        .concat(itemQueryString));

        try {
                response = itemRestTemplate.getForObject(
                    builder.toUriString(), ItemResponse.class);
            watch.stop();
            log.info("op={},status=OK,desc=successfully fetched item Details " +
                            "for the itemId {} in {} ms", "getItem", productId, watch.getTime());
        } catch (Exception excep) {
            throw new ProductException("ERR_ITEM_NOT_FOUND", excep);
        }
        return response;
    }

    @SuppressWarnings("unused")
    private ItemResponse callGetItemFallback() {
        throw new ProductException("ERR_ITEM_INTEGRATION");
    }

}
