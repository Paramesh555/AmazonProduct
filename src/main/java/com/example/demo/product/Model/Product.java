package com.example.demo.product.Model;

import com.example.demo.category.Category;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = "product")
public class Product {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID Id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @ManyToOne
    @JoinColumn(name = "category_id")//the new column added in sql table will be the category_id

    private Category category;

    @CreationTimestamp
    private Date timeStampCreated;

    @UpdateTimestamp
    private Date timeStampUpdated;

    @Enumerated(EnumType.STRING)
    private Region region;

    @Column(name = "manufacturer")
    private String manufacturer;

}
