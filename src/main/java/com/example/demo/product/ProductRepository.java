package com.example.demo.product;

import com.example.demo.product.Model.Product;
import com.example.demo.product.Model.Region;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    //u can use a custom query -> spring data
    //do not do native query

    @Query ("SELECT p FROM Product p WHERE " +
            "(:nameOrDescription is null or p.name like %:nameOrDescription% or p.description like %:nameOrDescription%) and "+
            "(p.region = :region) and " +
            "(:category is null or p.category.name = :category)")
    List<Product> findByNameOrDescriptionAndRegionAndCategory(
            String nameOrDescription,
            Region region,
            String category,
            Sort sort
    );
}
