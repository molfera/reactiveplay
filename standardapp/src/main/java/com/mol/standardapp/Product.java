package com.mol.standardapp;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
@Setter
class Product {

    private UUID series;
    private Integer numberInSeries;

    @Id
    private String id;
    private String name;
    private String brand;
    private BigDecimal price;

    public Product() {
    }

    public Product(UUID series, Integer numberInSeries, String id, String name, String brand, BigDecimal price) {
        this.series = series;
        this.numberInSeries = numberInSeries;
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
    }
}
