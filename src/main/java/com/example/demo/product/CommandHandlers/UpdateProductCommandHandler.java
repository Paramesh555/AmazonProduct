package com.example.demo.product.CommandHandlers;

import com.example.demo.Command;
import com.example.demo.Exceptions.ProductNotFoundException;
import com.example.demo.category.Category;
import com.example.demo.category.CategoryRepository;
import com.example.demo.product.Model.Product;
import com.example.demo.product.Model.ProductDTO;
import com.example.demo.product.Model.Region;
import com.example.demo.product.Model.UpdateProductCommand;
import com.example.demo.product.ProductRepository;
import com.example.demo.product.Model.ProductRequest;
import com.example.demo.product.productValidation.ProductValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UpdateProductCommandHandler implements Command<UpdateProductCommand,ProductDTO> {

    private final Logger logger = LoggerFactory.getLogger(UpdateProductCommand.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductValidator productValidator;

    @Override
    public ResponseEntity<ProductDTO> execute(UpdateProductCommand command) {
        logger.info("update product command Handler {} {} {}", command.getId(), command.getRequest(), getClass().getSimpleName());

        ProductRequest productDetails = command.getRequest();
        String id = command.getId();
        Optional<Product> optionalProduct = productRepository.findById(UUID.fromString(id));
        if(optionalProduct.isEmpty()){
            throw new ProductNotFoundException();
        }
        List<Category> availableCategories = categoryRepository.findAll();
        productValidator.execute(productDetails,availableCategories);

        Product product = optionalProduct.get();
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setManufacturer(productDetails.getManufacturer());
        product.setRegion(Region.valueOf(productDetails.getRegion()));
        product.setCategory(new Category(productDetails.getCategory()));
        productRepository.save(product);
        return ResponseEntity.ok(new ProductDTO(product));
    }
}
