package com.example.demo.product.Model;

import lombok.Data;

@Data
public class UpdateProductCommand {
    private String id;
    private ProductRequest request;

    public UpdateProductCommand(String id, ProductRequest request) {
        this.request = request;
        this.id = id;
    }
}
