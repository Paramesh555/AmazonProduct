package com.example.demo.category;

import com.example.demo.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetCategoryQueryHandler implements Query<Void, List<String>> {
    @Autowired
    private CategoryRepository categoryRepository;

    private final Logger logger = LoggerFactory.getLogger(GetCategoryQueryHandler.class);

    @Override
    public ResponseEntity<List<String>> execute(Void input) {
        logger.info("Executing {} GetCategoryQueryHandler",getClass().getSimpleName());
        return ResponseEntity.ok(categoryRepository
                .findAll()
                .stream()
                .map(Category::getName)
                .collect(Collectors.toList())
        );
    }
}
