package com.myretail.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories(basePackages = "com.myretail")
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${cassandra.keyspace}")
    private String keySpace;

    @Value("${cassandra.contactpoints}")
    private String contactPoints;

    @Value("${cassandra.port}")
    private Integer port;

    @Override
    public String getKeyspaceName(){
        return keySpace;
    }

    @Override
    public CassandraClusterFactoryBean cluster() {
        final CassandraClusterFactoryBean clusterBean = new CassandraClusterFactoryBean();
        clusterBean.setContactPoints(contactPoints);
        clusterBean.setPort(port);
        return clusterBean;
    }

    @Override
    public CassandraMappingContext cassandraMapping() {
        return new CassandraMappingContext();
    }
}
