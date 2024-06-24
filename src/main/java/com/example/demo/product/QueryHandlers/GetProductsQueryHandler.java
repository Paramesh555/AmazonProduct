package com.example.demo.product.QueryHandlers;

import com.example.demo.Query;
import com.example.demo.product.Model.GetProductsQuery;
import com.example.demo.product.Model.Product;
import com.example.demo.product.Model.ProductDTO;
import com.example.demo.product.Model.ProductSortBy;
import com.example.demo.product.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetProductsQueryHandler implements Query<GetProductsQuery, List<ProductDTO>> {

    private static final Logger logger = LoggerFactory.getLogger(GetProductsQueryHandler.class);

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ResponseEntity<List<ProductDTO>> execute(GetProductsQuery query) {
        Sort productSort = defineSort(query.getProductSortBy());
        logger.info("Get Products Query Handler query : {}",query);
        List<Product> productList = productRepository
                .findByNameOrDescriptionAndRegionAndCategory(
                        query.getNameOrDescription(),
                        query.getRegion(),
                        query.getCategory(),
                        productSort
                );
        return ResponseEntity.ok(productList.stream().map(ProductDTO :: new).collect(Collectors.toList()));
    }

    public Sort defineSort(ProductSortBy productSortBy) {
        //if null -> unsorted
        if(productSortBy == null){
            return Sort.unsorted();
        }
        ProductSortBy sortBy = productSortBy.valueOf(productSortBy.getValue());
        return Sort.by(String.valueOf(sortBy));
    }
}
