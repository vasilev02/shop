package com.example.shop.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity class for the Subscriber.
 * This class represents a subscriber in the shop.
 * It includes details like the first name, last name of the subscriber, and the products associated with the subscriber.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Subscriber extends BaseEntity {

    private String firstName;

    private String lastName;

    private LocalDateTime joinedDate;

    @JsonManagedReference
    @ManyToMany
    @JoinTable(
            name = "subscriber_product",
            joinColumns = @JoinColumn(name = "subscriber_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;
}