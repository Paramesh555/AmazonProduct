package com.example.demo.category;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Category {
    @Id
    private String name;

    public Category(String name){
        this.name = name;
    }
}
