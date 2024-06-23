package com.example.demo.product.QueryHandlers;

import com.example.demo.Exceptions.ProductNotFoundException;
import com.example.demo.product.Model.Product;
import com.example.demo.Query;
import com.example.demo.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetProductByIdQueryHandler implements Query<String, Product> {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ResponseEntity<Product> execute(String id) {
        Optional<Product> product = productRepository.findById(UUID.fromString(id));
        if(product.isEmpty()){
            throw new ProductNotFoundException();
        }
       return ResponseEntity.ok(product.get());

    }
}
