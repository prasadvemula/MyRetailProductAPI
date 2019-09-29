package com.myretail.service.price.impl;

import com.myretail.respository.price.PriceRepository;
import com.myretail.respository.entity.Price;
import com.myretail.rest.product.request.PriceRequest;
import com.myretail.service.price.PriceService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

@Service
@Configuration
@NoArgsConstructor
@AllArgsConstructor
public class PriceServiceImpl implements PriceService {

    @Autowired
    private PriceRepository priceRepository;

    @Override
    public Price fetchPrice(String id){
        Price price = priceRepository.fetchPrice(id);
        return price;
    }

    @Override
    public Price createPrice(PriceRequest priceRequest){
        ModelMapper mapper = new ModelMapper();
        Price price = mapper.map(priceRequest, Price.class);
        priceRepository.createPrice(price);
        return price;
    }

    @Override
    public Price updatePrice(PriceRequest priceRequest) {
        Price price = priceRepository.fetchPrice(priceRequest.getProductId());
        price.setPrice(priceRequest.getPrice());
        priceRepository.updatePrice(price);
        return price;
    }

}
