package com.myretail.service.price;

import com.myretail.respository.entity.Price;
import com.myretail.respository.price.PriceRepository;
import com.myretail.mock.ProductMockData;
import com.myretail.rest.product.request.PriceRequest;
import com.myretail.service.price.impl.PriceServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PriceServiceImplTest {

    @Mock
    private PriceRepository priceRepository;

    private PriceServiceImpl priceServiceImpl;

    @Before
    public void setup() throws Exception{
        priceServiceImpl = new PriceServiceImpl(priceRepository);
    }

    @Test
    public void testGetPrice() throws  Exception{
        Price response = ProductMockData.getPriceResponse();
        when(priceRepository.fetchPrice(any())).thenReturn(response);
        Price price = priceServiceImpl.fetchPrice("123");
        Assert.assertEquals(price.getProductId(), response.getProductId());
        Assert.assertEquals(price.getPrice(), response.getPrice());
        Assert.assertEquals(price.getCurrency(), response.getCurrency());
    }

    @Test
    public void testCreatePrice() throws Exception {
        PriceRequest request = ProductMockData.getPriceRequest();
        Price response = ProductMockData.getPriceResponse();
        when(priceRepository.createPrice(any())).thenReturn(response);
        Price price = priceServiceImpl.createPrice(request);
        Assert.assertEquals(price.getProductId(), response.getProductId());
        Assert.assertEquals(price.getCurrency(), response.getCurrency());
        Assert.assertEquals(price.getPrice(), response.getPrice());
    }

}
