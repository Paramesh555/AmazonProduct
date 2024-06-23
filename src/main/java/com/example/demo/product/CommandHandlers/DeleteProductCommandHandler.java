package com.example.demo.product.CommandHandlers;

import com.example.demo.Command;
import com.example.demo.Exceptions.ProductNotFoundException;
import com.example.demo.product.Model.Product;
import com.example.demo.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DeleteProductCommandHandler implements Command<String,Void> {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public ResponseEntity execute(String id) {
        Optional<Product> product = productRepository.findById(UUID.fromString(id));
        if(product.isEmpty()){
            throw new ProductNotFoundException();
        }
        productRepository.delete(product.get());
        return ResponseEntity.ok().build();
    }
}
