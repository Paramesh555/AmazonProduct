package com.example.demo.product;

import com.example.demo.product.CommandHandlers.CreateProductCommandHandler;
import com.example.demo.product.CommandHandlers.DeleteProductCommandHandler;
import com.example.demo.product.CommandHandlers.UpdateProductCommandHandler;
import com.example.demo.product.Model.*;
import com.example.demo.product.QueryHandlers.GetProductByIdQueryHandler;
import com.example.demo.product.QueryHandlers.GetProductsQueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private GetProductByIdQueryHandler getProductByIdQueryHandler;

    @Autowired
    private CreateProductCommandHandler createProductCommandHandler;

    @Autowired
    private GetProductsQueryHandler getProductsQueryHandler;

    @Autowired
    private UpdateProductCommandHandler updateProductCommandHandler;

    @Autowired
    private DeleteProductCommandHandler deleteProductCommandHandler;

    @GetMapping("/{id}")
    public ResponseEntity getProductByID(@PathVariable String id){
        return getProductByIdQueryHandler.execute(id);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductRequest request){
        return createProductCommandHandler.execute(request);
    }



    @GetMapping
    @Cacheable("products")
    public ResponseEntity<List<ProductDTO>> getProducts(
            @RequestHeader(value = "region",defaultValue = "US") String region,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String nameOrDescription,
            @RequestParam(required = false) String orderBy
    ){
        return getProductsQueryHandler.execute(new GetProductsQuery(
                Region.valueOf(region),
                category,
                nameOrDescription,
                ProductSortBy.fromValue(orderBy)
            ));
    }



    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable String id,@RequestBody ProductRequest request){
        UpdateProductCommand command = new UpdateProductCommand(id,request);
        return updateProductCommandHandler.execute(command);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable String id){
        return deleteProductCommandHandler.execute(id);
    }
}
