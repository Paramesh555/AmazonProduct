package com.example.demo.product.CommandHandlers;

import com.example.demo.Command;
import com.example.demo.category.CategoryRepository;
import com.example.demo.category.Category;
import com.example.demo.product.Model.Product;
import com.example.demo.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateProductCommandHandler implements Command<Product, ResponseEntity> {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ResponseEntity execute(Product product) {
        Optional<Category> category = categoryRepository.findById(product.getCategory().getName());
        if(category.isPresent()){
            //we have that category so the jpa should point to that instead of creating new one
            product.setCategory(category.get());
        }
        productRepository.save(product);
        return ResponseEntity.ok().build();
    }
}
