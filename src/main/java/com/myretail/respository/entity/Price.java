package com.myretail.respository.entity;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;

@Table
@Data
public class Price implements Serializable {

    @PrimaryKey
    private String productId;

    private String price;

    private String currency;

}
