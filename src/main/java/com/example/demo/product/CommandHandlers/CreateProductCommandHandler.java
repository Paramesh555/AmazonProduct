package com.example.demo.product.CommandHandlers;

import com.example.demo.Command;
import com.example.demo.category.CategoryRepository;
import com.example.demo.product.Model.Product;
import com.example.demo.product.Model.ProductDTO;
import com.example.demo.product.ProductRepository;
import com.example.demo.product.Model.ProductRequest;
import com.example.demo.product.productValidation.ProductValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class CreateProductCommandHandler implements Command<ProductRequest, ProductDTO> {

    private final Logger logger = LoggerFactory.getLogger(CreateProductCommandHandler.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductValidator productValidator;

    @Override
    public ResponseEntity<ProductDTO> execute(ProductRequest request) {
        logger.info("create Product Command Handler "+request+" "+getClass().getSimpleName());
        //product validator
        Product validatedProduct = productValidator.execute(request,categoryRepository.findAll());
        productRepository.save(validatedProduct);

        return ResponseEntity.ok(new ProductDTO(validatedProduct));
    }
}
