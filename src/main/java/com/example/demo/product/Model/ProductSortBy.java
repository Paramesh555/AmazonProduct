package com.example.demo.product.Model;

public enum ProductSortBy {
    name("name"),
    price("price");

    private final String value;

    ProductSortBy(String value) {
        this.value = value;
    }

    public static ProductSortBy fromValue(String value){
        for(ProductSortBy sortBy:ProductSortBy.values()){
            if(sortBy.value.equalsIgnoreCase(value)){
                return sortBy;
            }
        }
        return null;
    }

    public String getValue(){
       return this.value;
    }
}
