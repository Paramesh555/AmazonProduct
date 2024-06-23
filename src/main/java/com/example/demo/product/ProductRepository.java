package com.example.demo.product;

import com.example.demo.product.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:descriptionOrName% OR p.description LIKE %:descriptionOrName%")
    List<Product> customQueryMethod(@Param("descriptionOrName") String val);

    @Query("select p from Product p where p.category.name like %:category%")
    List<Product> getProductsByCategory(@Param("category") String category);
}
