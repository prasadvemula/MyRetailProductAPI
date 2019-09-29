package com.myretail.facade;

import com.myretail.config.ApiConfig;
import com.myretail.facade.impl.ItemFacadeImpl;
import com.myretail.mock.ProductMockData;
import com.myretail.rest.product.response.ItemResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ItemFacadeImplTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ApiConfig apiConfig;

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    private ItemFacadeImpl itemFacadeImpl;

    private static String itemQueryString = "?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";

    @Before
    public void setup() {
        when(apiConfig.getItemApiBaseURL()).thenReturn("url");
        when(apiConfig.getItemApiConnectionTimeout()).thenReturn(50);
        when(apiConfig.getItemApiReadTimeout()).thenReturn(50);
        when(restTemplateBuilder.rootUri(anyString())).thenReturn(restTemplateBuilder);
        when(restTemplateBuilder.setConnectTimeout(anyInt())).thenReturn(restTemplateBuilder);
        when(restTemplateBuilder.setReadTimeout(anyInt())).thenReturn(restTemplateBuilder);
        when(restTemplateBuilder.build()).thenReturn(restTemplate);

        itemFacadeImpl = new ItemFacadeImpl(restTemplateBuilder, apiConfig);
    }

    @Test
    public void tetGetItem() {
        ItemResponse mockResponse = ProductMockData.getItemDetails();
        UriComponentsBuilder uriBuilder
                = UriComponentsBuilder.fromUriString(apiConfig.getItemApiBaseURL()
                    .concat("/pdp/tcin/").concat(ProductMockData.PRODUCT_ID)
                    .concat(itemQueryString));
        when(restTemplate.getForObject(uriBuilder.toUriString(), ItemResponse.class))
                .thenReturn(mockResponse);

        ItemResponse response = itemFacadeImpl.getItem(ProductMockData.PRODUCT_ID);
        Assert.assertNotNull(response);
        Assert.assertEquals(ProductMockData.getItemDetails().getProduct(), response.getProduct());

    }

}
