package com.alexandre.estoque.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String barcode;

    private Integer quantity;
    private Integer minQuantity;
    private BigDecimal price;

    public Product() {}
    public Product(String name, String barcode, Integer quantity, Integer minQuantity, BigDecimal price) {
        this.name = name;
        this.barcode = barcode;
        this.quantity = quantity;
        this.minQuantity = minQuantity;
        this.price = price;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getBarcode() { return barcode; }
    public Integer getQuantity() { return quantity; }
    public Integer getMinQuantity() { return minQuantity; }
    public BigDecimal getPrice() { return price; }
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setBarcode(String barcode) { this.barcode = barcode; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public void setMinQuantity(Integer minQuantity) { this.minQuantity = minQuantity; }
    public void setPrice(BigDecimal price) { this.price = price; }
}
