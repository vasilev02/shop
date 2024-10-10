package com.example.shop.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity class for the Product.
 * This class represents a product in the shop.
 * It includes details like the name of the product, its creation date, whether it's under sale, and the subscribers associated with it.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product extends BaseEntity {

    private String name;

    private LocalDateTime creationDate;

    private boolean isUnderSale;

    @JsonBackReference
    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<Subscriber> subscribers;
}
