package com.example.demo.product.CommandHandlers;

import com.example.demo.Command;
import com.example.demo.product.Model.Product;
import com.example.demo.product.Model.UpdateProductCommand;
import com.example.demo.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UpdateProductCommandHandler implements Command<UpdateProductCommand, Void> {
    @Autowired
    private ProductRepository productRepository;


    @Override
    @CacheEvict(value = "productCache",allEntries = true)
    public ResponseEntity execute(UpdateProductCommand command) {
        Product productDetails = command.getProduct();
        String id = command.getId();
        Optional<Product> optionalProduct = productRepository.findById(UUID.fromString(id));
        if(optionalProduct.isEmpty()){
            throw new RuntimeException("product is not there");
        }
        Product product = optionalProduct.get();
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setManufacturer(productDetails.getManufacturer());
        product.setRegion(productDetails.getRegion());
        productRepository.save(product);
        return ResponseEntity.ok().build();
    }
}
