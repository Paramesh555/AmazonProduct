package com.example.demo.product;

import com.example.demo.Exceptions.InvalidProductException;
import com.example.demo.Test1ApplicationTests;
import com.example.demo.category.Category;
import com.example.demo.product.Model.ProductRequest;
import com.example.demo.product.productValidation.ProductValidator;
import com.example.demo.product.productValidation.ProfanityValidator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = Test1ApplicationTests.class)
public class ProductValidatorTest {

    @InjectMocks
    private ProductValidator productValidator;

    @Mock
    private ProfanityValidator profanityValidator;

    @Test
    void testNameIsEmpty_throwsInvalidProductException(){
        ProductRequest request = getValidProductRequest();
        request.setName(null);

        InvalidProductException exception= assertThrows(InvalidProductException.class,
                ()-> productValidator.execute(request,getCategories()));
        assertEquals("Product Name Cannot Be Empty",exception.getSimpleResponse().getMessage());

    }

    @Test
    void testPriceIsNegative_throwsInvalidProductException(){
        ProductRequest request = getValidProductRequest();
        request.setPrice(-9.8);

        InvalidProductException exception= assertThrows(InvalidProductException.class,
                ()-> productValidator.execute(request,getCategories()));
        assertEquals("Product Price cannot be negative or null",exception.getSimpleResponse().getMessage());
    }

    @Test
    void testCategoryNotAvailable_throwsInvalidProductException(){
        ProductRequest request = getValidProductRequest();
        request.setCategory("foot wear");

        InvalidProductException exception= assertThrows(InvalidProductException.class,
                ()-> productValidator.execute(request,getCategories()));
        assertEquals("Product Category is not available",exception.getSimpleResponse().getMessage());
    }

    @Test
    void testRegionNotAvailable_throwsInvalidProductException(){
        ProductRequest request = getValidProductRequest();
        request.setRegion("INDIA");

        InvalidProductException exception= assertThrows(InvalidProductException.class,
                ()-> productValidator.execute(request,getCategories()));
        assertEquals("Product Region is not Available",exception.getSimpleResponse().getMessage());
    }

    @Test
    void testProductHasProfanity_throwsInvalidProductException(){
        ProductRequest request = getValidProductRequest();
        when(profanityValidator.hasProfanity(anyString(),anyString())).thenReturn(true);

        InvalidProductException exception= assertThrows(InvalidProductException.class,
                ()-> productValidator.execute(request,getCategories()));

        assertEquals("Product has profanity",exception.getSimpleResponse().getMessage());
    }


    private ProductRequest getValidProductRequest(){
        return new ProductRequest("testName","testDescription","testManufacturer",19.99,"US","Electronics");
    }
    private List<Category> getCategories(){
        return Arrays.asList(new Category("Electronics"),new Category("food"));
    }
}
