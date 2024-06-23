package com.example.demo.product.Model;

import com.example.demo.category.Category;
import lombok.Data;

@Data
public class ProductDTO {
    private String name;
    private String description;
    private Double price;
    private String manufacturer;
    private Category category;

    public ProductDTO(Product product){
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.manufacturer = product.getManufacturer();
        this.category = product.getCategory();
    }
    public ProductDTO(String name, String description, Double price,String manufacturer,Category category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.manufacturer = manufacturer;
        this.category = category;
    }
}
