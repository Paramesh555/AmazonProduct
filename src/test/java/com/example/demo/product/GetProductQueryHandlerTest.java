package com.example.demo.product;

import com.example.demo.Exceptions.ProductNotFoundException;
import com.example.demo.Test1ApplicationTests;
import com.example.demo.category.Category;
import com.example.demo.product.Model.Product;
import com.example.demo.product.QueryHandlers.GetProductByIdQueryHandler;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = Test1ApplicationTests.class)
public class GetProductQueryHandlerTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private GetProductByIdQueryHandler getProductByIdQueryHandler;

    @Test
    void getProductByIdQueryHandler_returnSuccess(){
        UUID id =UUID.randomUUID();
        Product product = new Product();
        product.setId(id);
        product.setCategory(new Category("idk"));

        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        ResponseEntity<Product> responseEntity = getProductByIdQueryHandler.execute(id.toString());
        verify(productRepository).findById(id);
        assertEquals(product,responseEntity.getBody());
    }

    @Test
    void getProductByIdQueryHandler_throwsProductNotFound(){
        UUID id =UUID.randomUUID();
        Product product = new Product();
        product.setId(id);
        product.setCategory(new Category("idk"));

        when(productRepository.findById(id)).thenReturn(Optional.empty());
        ProductNotFoundException exception =assertThrows(ProductNotFoundException.class,()
                            ->getProductByIdQueryHandler.execute(id.toString()));
        assertEquals("Product Not Found",exception.getSimpleResponse().getMessage());
    }
}
