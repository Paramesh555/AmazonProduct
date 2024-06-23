package com.example.demo.product;

import com.example.demo.Exceptions.ProductNotFoundException;
import com.example.demo.Test1ApplicationTests;
import com.example.demo.product.CommandHandlers.DeleteProductCommandHandler;
import com.example.demo.product.Model.Product;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = Test1ApplicationTests.class)
public class DeleteProductCommandHandlerTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private DeleteProductCommandHandler deleteProductCommandHandler;



    @Test
    void deleteProductCommandHandler_returnSuccess(){
        UUID id = UUID.randomUUID();
        Product product = new Product();
        product.setId(id);

        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        ResponseEntity responseEntity = deleteProductCommandHandler.execute(id.toString());
        verify(productRepository).delete(product);//it checks whether delete method is called on productRepository
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }

    @Test
    void deleteProductCommandHandler_throwsProductNotFoundException(){
        UUID id =UUID.randomUUID();
        Product product = new Product();
        product.setId(id);

        when(productRepository.findById(id)).thenReturn(Optional.empty());
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,()
                ->deleteProductCommandHandler.execute(id.toString()));
        assertEquals("Product Not Found",exception.getSimpleResponse().getMessage());
    }
}
