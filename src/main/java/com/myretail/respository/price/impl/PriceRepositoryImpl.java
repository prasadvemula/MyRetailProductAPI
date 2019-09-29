package com.myretail.respository.price.impl;

import com.myretail.respository.price.PriceRepository;
import com.myretail.respository.entity.Price;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.StopWatch;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class PriceRepositoryImpl implements PriceRepository {

    private final CassandraOperations cassandraOperations;


    public PriceRepositoryImpl(CassandraOperations cassandraOperations) {
        this.cassandraOperations = cassandraOperations;
    }

    @Override
    public Price fetchPrice(String productId) {
        StopWatch watch = new StopWatch();
        watch.start();
        Price price = cassandraOperations.selectOneById(productId, Price.class);
        watch.stop();
        log.info("op=fetchPrice,status=OK,desc=successfully fetch price in {} ms",
                 watch.getTime());
        return price;
    }

    @Override
    public Price createPrice(Price price) {
        StopWatch watch = new StopWatch();
        watch.start();
        cassandraOperations.insert(price);
        watch.stop();
        log.info("op=createPrice,status=OK,desc=successfully price created in {} ms",
                watch.getTime());
        return price;
    }

    @Override
    public Price updatePrice(Price price) {
        StopWatch watch = new StopWatch();
        watch.start();
        cassandraOperations.update(price);
        watch.stop();
        log.info("op=updatePrice,status=OK,desc=successfully price updated in {} ms",
                watch.getTime());
        return price;
    }

}
