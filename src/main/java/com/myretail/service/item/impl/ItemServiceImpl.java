package com.myretail.service.item.impl;

import com.myretail.facade.ItemFacade;
import com.myretail.rest.product.response.ItemResponse;
import com.myretail.rest.product.response.ProductResponse;
import com.myretail.service.item.ItemService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    private ItemFacade itemFacade;

    @Autowired
    public ItemServiceImpl(ItemFacade itemFacade) {
        this.itemFacade = itemFacade;
    }

    @Override
    public ProductResponse getProductDetails(String productId) {
        ItemResponse item = itemFacade.getItem(productId);
        ProductResponse product = new ProductResponse();
        if (null != item && null != item.getProduct() && null != item.getProduct().getItem()) {
            product.setProductId(item.getProduct().getItem().getTcin());
            product.setTitle(item.getProduct().getItem().getProductDescription().getTitle());
        }
        return product;
    }
}
