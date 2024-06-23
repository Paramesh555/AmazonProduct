package com.example.demo.product.QueryHandlers;

import com.example.demo.Query;
import com.example.demo.product.Model.Product;
import com.example.demo.product.Model.ProductDTO;
import com.example.demo.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class GetProductsQueryHandler implements Query<Void, List<ProductDTO>> {

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Cacheable(value = "productCache")
    public ResponseEntity<List<ProductDTO>> execute(Void input) {
        List<Product> products = productRepository.findAll();
        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                if(o1.getPrice() > o2.getPrice()){
                    return -1;
                }else if(o1.getPrice() < o2.getPrice()){
                    return 1;
                }else{
                    return 0;
                }
            }
        });
        List<ProductDTO> productDTOS = new ArrayList<>();
        for(Product product :products){
            productDTOS.add(new ProductDTO(product));
        }
        return ResponseEntity.ok(productDTOS);
    }
}
