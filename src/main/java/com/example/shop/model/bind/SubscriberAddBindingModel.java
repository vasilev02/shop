package com.example.shop.model.bind;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Binding model for adding a new product.
 * This class is used to capture the data entered by the user when adding a new subscriber.
 */
public class SubscriberAddBindingModel {

    private String firstName;

    private String lastName;

    public SubscriberAddBindingModel() {
    }

    @NotNull(message = "First name cannot be null")
    @Size(min = 3, max = 15, message = "First name must be between 3 and 15 characters")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotNull(message = "Last name cannot be null")
    @Size(min = 3, max = 15, message = "Last name must be between 3 and 15 characters")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
