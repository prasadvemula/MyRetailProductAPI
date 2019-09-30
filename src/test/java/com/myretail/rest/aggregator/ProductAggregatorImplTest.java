package com.myretail.rest.aggregator;

import com.myretail.mock.ProductMockData;
import com.myretail.respository.entity.Price;
import com.myretail.rest.aggregator.impl.ProductAggregatorImpl;
import com.myretail.rest.product.response.ProductResponse;
import com.myretail.service.item.ItemService;
import com.myretail.service.price.PriceService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductAggregatorImplTest {

    ProductAggregatorImpl productAggregator;

    @Mock
    private PriceService priceService;

    @Mock
    private ItemService itemService;

    @Before
    public void setUp() throws Exception {
        productAggregator = new ProductAggregatorImpl(itemService, priceService);
    }

    @Test
    public void testFetchProductWithPrice() throws Exception {
        Price mockPrice = ProductMockData.getPriceResponse();
        ProductResponse mockProduct = ProductMockData.getProductDetails();
        when(priceService.fetchPrice(ProductMockData.PRODUCT_ID))
                .thenReturn(mockPrice);
        when(itemService.getProductDetails(ProductMockData.PRODUCT_ID))
                .thenReturn(mockProduct);

        ProductResponse product = productAggregator.fetchProduct(ProductMockData.PRODUCT_ID);
        Assert.assertEquals(product.getProductId(), ProductMockData.PRODUCT_ID);
        Assert.assertEquals(product.getTitle(), ProductMockData.PRODUCT_TITLE);
        Assert.assertEquals(product.getCurrentPrice().getCurrency(), mockPrice.getCurrency());
        Assert.assertEquals(product.getCurrentPrice().getValue(), mockPrice.getPrice());
    }

    @Test
    public void testFetchProductWithoutPrice() throws Exception {
        ProductResponse mockProduct = ProductMockData.getProductDetails();
        when(priceService.fetchPrice(ProductMockData.PRODUCT_ID))
                .thenReturn(null);
        when(itemService.getProductDetails(ProductMockData.PRODUCT_ID))
                .thenReturn(mockProduct);

        ProductResponse product = productAggregator.fetchProduct(ProductMockData.PRODUCT_ID);
        Assert.assertEquals(product.getProductId(), ProductMockData.PRODUCT_ID);
        Assert.assertEquals(product.getTitle(), ProductMockData.PRODUCT_TITLE);
        Assert.assertEquals(new ProductResponse.Price(), product.getCurrentPrice());
    }

    @Test
    public void testAggregateWhenReturnPrice() throws Exception {
        ProductResponse product
                = productAggregator.aggregate(ProductMockData.getProductDetails(), null);
        Assert.assertEquals(product.getProductId(), ProductMockData.PRODUCT_ID);
        Assert.assertEquals(product.getTitle(), ProductMockData.PRODUCT_TITLE);
        Assert.assertEquals(product.getCurrentPrice(), null);
    }

    @Test
    public void testAggregateWhenReturnNoPrice() throws Exception {
        Price mockPrice = ProductMockData.getPriceResponse();
        ProductResponse product = productAggregator.aggregate(ProductMockData.getProductDetails(), mockPrice);
        Assert.assertEquals(product.getProductId(), ProductMockData.PRODUCT_ID);
        Assert.assertEquals(product.getTitle(), ProductMockData.PRODUCT_TITLE);
        Assert.assertEquals(product.getCurrentPrice().getCurrency(), mockPrice.getCurrency());
        Assert.assertEquals(product.getCurrentPrice().getValue(), mockPrice.getPrice());
    }
}
