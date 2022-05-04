package com.xlw.demo.entity;

import lombok.Data;

@Data
public class Product {
    private Integer id;
    private String productName;
    private Integer stock;
    private Integer version;
}
