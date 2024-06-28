package com.example.demo.product.productValidation;

import com.example.demo.Exceptions.InvalidProductException;
import com.example.demo.Exceptions.SimpleResponse;
import com.example.demo.category.Category;
import com.example.demo.product.Model.Product;
import com.example.demo.product.Model.Region;
import com.example.demo.product.Model.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductValidator {

    @Autowired
    private ProfanityValidator profanityValidator;

    public  Product execute(ProductRequest request, List<Category> availableCategories){
        if(nameIsEmpty(request.getName())){
            throw new InvalidProductException(new SimpleResponse("Product Name Cannot Be Empty"),request);
        }

        if(priceIsNegativeOrNull(request.getPrice())){
            throw  new InvalidProductException(new SimpleResponse("Product Price cannot be negative or null"),request);
        }
         if(categoryNotAvailable(request.getCategory(),availableCategories)){
             throw new InvalidProductException(new SimpleResponse("Product Category is not available"),request);
         }

         if(regionNotAvailable(request.getRegion())){
             throw new InvalidProductException(new SimpleResponse("Product Region is not Available"),request);
         }

         //profanity validator

        if(profanityValidator.hasProfanity(request.getName(),request.getDescription())){
            throw new InvalidProductException(new SimpleResponse("Product has profanity"),request);
        }

        return new Product(request);
    }

    private static boolean regionNotAvailable(String userRegion) {
        for(Region region : Region.values()){
            if(region.name().equals(userRegion)){
                return false;
            }
        }
        return true;
    }

    private static boolean categoryNotAvailable(String category, List<Category> availableCategories) {
        for(Category category1 : availableCategories){
            if(category1.getName().equals(category)){
                return false;
            }
        }
        return true;
    }

    private static boolean priceIsNegativeOrNull(Double price) {
            return price == null || price <0.0;
    }

    private static boolean nameIsEmpty(String name) {
            return name == null || name.isEmpty();
    }

}
