package com.myretail.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories(basePackages = "com.myretail")
@Data
public class ApiConfig {

    @Value("${item.api.base_url}")
    private String itemApiBaseURL;

    @Value("${item.api.connection_timeout}")
    private int itemApiConnectionTimeout;

    @Value("${item.api.read_timeout}")
    private int itemApiReadTimeout;

}
