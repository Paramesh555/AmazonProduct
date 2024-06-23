package com.example.demo.category;

import com.example.demo.Test1ApplicationTests;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = Test1ApplicationTests.class)
public class GetCategoryQueryHandlerTest {

    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private GetCategoryQueryHandler getCategoryQueryHandler;



    @Test
    public void categoryQueryHandler_returnsList(){
        List<Category> categories = Arrays.asList(
                new Category("Electronics"),
                new Category("Bathroom"),
                new Category("Automobile")
        );
        when(categoryRepository.findAll()).thenReturn(categories);
        List<String> expected = Arrays.asList("Electronics","Bathroom","Automobile");
        ResponseEntity<List<String>> actual = getCategoryQueryHandler.execute(null);
        assertEquals(expected,actual.getBody());
    }


}
