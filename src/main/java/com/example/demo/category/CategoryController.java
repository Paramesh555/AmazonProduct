package com.example.demo.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private GetCategoryQueryHandler getCategoryQueryHandler;

    @GetMapping("/category")
    private ResponseEntity<List<String>> getCategories(){
        return getCategoryQueryHandler.execute(null);
    }
}
