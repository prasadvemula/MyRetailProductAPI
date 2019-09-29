package com.myretail.rest.product.controller;

import com.myretail.mock.ProductMockData;
import com.myretail.rest.aggregator.ProductAggregator;
import com.myretail.service.price.PriceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    ProductController productController;
    private MockMvc mockMvc;

    @Mock
    private PriceService priceService;

    @Mock
    ProductAggregator productAggregator;

    @Before
    public void setup() throws  Exception{
        productController = new ProductController(priceService, productAggregator);
        mockMvc = standaloneSetup(productController).build();
    }

    @Test
    public void testItemDetailsFetched() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/product/12345678").
                contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).andReturn();
        verify(productAggregator, times(1)).fetchProduct("12345678");
    }

    @Test
    public void testPriceCreatedSuccessfully() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/price")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ProductMockData.makeCreatePriceRequestPayload()))
                .andExpect(status().isCreated()).andReturn();
        verify(priceService, times(1)).createPrice(eq(ProductMockData.getPriceRequest()));
    }

    @Test
    public void testPriceUpdatedSuccessfully() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/price/12345678")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ProductMockData.makeUpdatePriceRequestPayload()))
                .andExpect(status().isOk()).andReturn();
        verify(priceService, times(1)).updatePrice(eq(ProductMockData.getPriceRequest()));
    }

}
