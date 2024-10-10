package com.example.shop.model.bind;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Binding model for adding a new product.
 * This class is used to capture the data entered by the user when adding a new product.
 */
public class ProductAddBindingModel {

    private String name;
    private Boolean isUnderSale;

    public ProductAddBindingModel() {
    }

    @NotNull(message = "Name cannot be null")
    @Size(min = 3, max = 15, message = "Name must be between 3 and 15 characters")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "Sale status is required")

    public Boolean getUnderSale() {
        return isUnderSale;
    }

    public void setIsUnderSale(Boolean isUnderSale) {
        this.isUnderSale = isUnderSale;
    }
}
