package com.myretail.rest.product.controller;

import com.myretail.exceptions.ProductException;
import com.myretail.respository.entity.Price;
import com.myretail.rest.aggregator.ProductAggregator;
import com.myretail.rest.product.request.PriceRequest;
import com.myretail.rest.product.response.ProductResponse;
import com.myretail.service.price.PriceService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class ProductController {

    @Autowired
    private PriceService priceService;

    @Autowired
    private ProductAggregator productAggregator;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ProductResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}
    )
    @RequestMapping(value = "/v1/product/{product_id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponse> getProductDetails(
            @PathVariable("product_id") String productId) throws ProductException {
        StopWatch watch = new StopWatch();
        watch.start();
        ProductResponse product = productAggregator.fetchProduct(productId);
        watch.stop();
        log.info("op={},status=OK,desc=successfully fetched item details for the productId {} " +
                        "in {} ms", "Item Details", productId, watch.getTime());
        return new ResponseEntity(product, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = ProductResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}
    )
    @RequestMapping(value = "/v1/price", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Price> createPriceById(
            @RequestBody(required = false) PriceRequest priceRequest) throws ProductException {
        StopWatch watch = new StopWatch();
        watch.start();
        Price price = priceService.createPrice(priceRequest);
        watch.stop();
        log.info("op={},status=OK,desc=successfully created price for the itemId {} in {} ms",
                "create price", priceRequest.getProductId(), watch.getTime());
        return new ResponseEntity(price, HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success", response = ProductResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}
    )
    @RequestMapping(value = "/v1/price/{product_id}", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Price> updatePriceById(
            @PathVariable("product_id") String productId,
            @RequestBody (required = false) PriceRequest priceRequest)
            throws ProductException {
        StopWatch watch = new StopWatch();
        watch.start();
        priceRequest.setProductId(productId);
        Price price = priceService.updatePrice(priceRequest);
        watch.stop();
        log.info("op={},status=OK,desc=successfully updated price details for the productId {} " +
                        "in {} ms", "Item Details", productId, watch.getTime());
        return new ResponseEntity(price, HttpStatus.OK);
    }
}
