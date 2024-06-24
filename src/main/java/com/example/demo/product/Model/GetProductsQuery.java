package com.example.demo.product.Model;

import lombok.Getter;

@Getter
public class GetProductsQuery {
    private Region region;
    private String category;
    private String nameOrDescription;
    private ProductSortBy productSortBy;

    public GetProductsQuery(Region region, String category,String nameOrDescription,ProductSortBy productSortBy) {
        this.region = region;
        this.category = category;
        this.nameOrDescription = nameOrDescription;
        this.productSortBy = productSortBy;
    }
}
