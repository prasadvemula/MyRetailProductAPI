package com.myretail.mock;

import com.myretail.respository.entity.Price;
import com.myretail.rest.product.request.PriceRequest;
import com.myretail.rest.product.response.ItemResponse;
import com.myretail.rest.product.response.ProductResponse;

public class ProductMockData {

    public static String PRODUCT_ID = "12345678";
    public static String PRODUCT_TITLE = "IPAD";
    private static String PRODUCT_PRICE = "26.99";
    private static String PRODUCT_PRICE_CURRENCY = "USD";

    public static Price getPriceResponse() {
        Price price = new Price();
        price.setProductId(PRODUCT_ID);
        price.setPrice(PRODUCT_PRICE);
        price.setCurrency(PRODUCT_PRICE_CURRENCY);
        return price;
    }

    public static PriceRequest getPriceRequest() {
        PriceRequest price = new PriceRequest();
        price.setProductId(PRODUCT_ID);
        price.setPrice(PRODUCT_PRICE);
        price.setCurrency(PRODUCT_PRICE_CURRENCY);
        return price;
    }

    public static ItemResponse getItemDetails() {
        ItemResponse response = new ItemResponse();
        ItemResponse.Product product = new ItemResponse.Product();
        ItemResponse.Item item = new ItemResponse.Item();
        ItemResponse.ProductDescription productDescription = new ItemResponse.ProductDescription();

        productDescription.setTitle(PRODUCT_TITLE);

        item.setTcin(PRODUCT_ID);
        item.setProductDescription(productDescription);

        product.setItem(item);

        response.setProduct(product);

        return response;
    }

    public static ProductResponse getProductDetails() {
        ProductResponse product = new ProductResponse();
        ItemResponse item  = getItemDetails();
        product.setTitle(item.getProduct().getItem().getProductDescription().getTitle());
        product.setProductId(item.getProduct().getItem().getTcin());
        return product;
    }

    public static String makeCreatePriceRequestPayload() {
        return "{\n" +
                "\t\"productId\":\"12345678\",\n" +
                "\t\"price\":\"26.99\",\n" +
                "\t\"currency\":\"USD\"\n" +
                "}";
    }

    public static String makeUpdatePriceRequestPayload() {
        return "{\n" +
                "\t\"price\":\"26.99\"\n" +
                "}";
    }

}
