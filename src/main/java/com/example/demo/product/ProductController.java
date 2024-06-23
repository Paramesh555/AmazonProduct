package com.example.demo.product;

import com.example.demo.product.CommandHandlers.CreateProductCommandHandler;
import com.example.demo.product.CommandHandlers.DeleteProductCommandHandler;
import com.example.demo.product.CommandHandlers.UpdateProductCommandHandler;
import com.example.demo.product.Model.Product;
import com.example.demo.product.Model.ProductDTO;
import com.example.demo.product.Model.UpdateProductCommand;
import com.example.demo.product.QueryHandlers.GetProductByIdQueryHandler;
import com.example.demo.product.QueryHandlers.GetProductsQueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity createPost(@RequestBody Product product){
        return createProductCommandHandler.execute(product);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> getProduct(@RequestParam(value = "descriptionOrName") String val){
        return ResponseEntity.ok(productRepository.customQueryMethod(val));
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        return getProductsQueryHandler.execute(null);
    }

    @GetMapping("/searchByCategory")
    public ResponseEntity<List<Product>> getProductBYCategory(@RequestParam(value = "category") String category){
        return ResponseEntity.ok(productRepository.getProductsByCategory(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity updateProduct(@PathVariable String id,@RequestBody Product product){
        UpdateProductCommand command = new UpdateProductCommand(id,product);
        return updateProductCommandHandler.execute(command);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable String id){
        return deleteProductCommandHandler.execute(id);
    }
}
