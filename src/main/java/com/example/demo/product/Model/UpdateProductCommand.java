package com.example.demo.product.Model;

import lombok.Data;

@Data
public class UpdateProductCommand {
    private String id;
    private Product product;

    public UpdateProductCommand(String id, Product product) {
        this.product = product;
        this.id = id;
    }
}
