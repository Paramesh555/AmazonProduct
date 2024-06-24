package com.example.demo.product;

import com.example.demo.Test1ApplicationTests;
import com.example.demo.category.Category;
import com.example.demo.product.Model.GetProductsQuery;
import com.example.demo.product.Model.Product;
import com.example.demo.product.Model.ProductDTO;
import com.example.demo.product.Model.ProductSortBy;
import com.example.demo.product.QueryHandlers.GetProductsQueryHandler;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = Test1ApplicationTests.class)
public class GetProductQueryHandlerTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private GetProductsQueryHandler getProductsQueryHandler;

    @Test
    void getProductsQueryHandler_returnEmptyList(){
        when(productRepository
                .findByNameOrDescriptionAndRegionAndCategory(null,null,null,null))
                .thenReturn(Collections.emptyList());

        ResponseEntity<List<ProductDTO>> response = getProductsQueryHandler
                .execute(new GetProductsQuery(null,null,null,null));
        assertEquals(Collections.emptyList(), response.getBody());
    }

    @Test
    void getProductsQueryHandler_returnList(){
        when(productRepository
                .findByNameOrDescriptionAndRegionAndCategory(any(),any(),any(),any()))
                .thenReturn(getProducts());
        ResponseEntity<List<ProductDTO>> response = getProductsQueryHandler
                .execute(new GetProductsQuery(null,null,null,null));
        List<ProductDTO> acutalList = response.getBody();
        assertEquals(2,acutalList.size());
    }

    @Test
    void defineSort_returnUnsorted(){
        Sort sort = getProductsQueryHandler.defineSort(null);
        assertEquals(Sort.unsorted(),sort);

    }

    @Test
    void defineSort_returnSorted(){
        Sort sort = getProductsQueryHandler.defineSort(ProductSortBy.name);
        assertEquals(Sort.by(Sort.Direction.ASC,"name"),sort);
    }
    private List<Product> getProducts(){
        Product product1 = new Product();
        product1.setId (UUID.randomUUID());
        product1.setCategory (new Category("Electronics"));
        Product product2 = new Product();
        product2. setId(UUID.randomUUID());
        product2.setCategory(new Category(  "Electronics"));
        return Arrays.asList(product2, product1);
    }
}
