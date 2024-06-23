package com.example.demo.product.Model;

import lombok.Getter;

@Getter
public class GetProductsQuery {
    private Region region;
    private String Category;
    private String nameOrDescription;
    private ProductSortBy productSortBy;

    public GetProductsQuery(Region region, String category, ProductSortBy productSortBy, String nameOrDescription) {
        this.region = region;
        Category = category;
        this.productSortBy = productSortBy;
        this.nameOrDescription = nameOrDescription;
    }
}
