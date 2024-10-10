package com.example.shop.controler;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;


/**
 * Utility class for common controller operations.
 * This class provides methods to handle common tasks in controllers such as error handling.
 */
public class ControllerUtils {

    public static final String PRODUCT_NOT_FOUND = "Product with id %d not found.";
    public static final String SUBSCRIBER_NOT_FOUND = "Subscriber with id %d not found.";

    private ControllerUtils() {
    }

    /**
     * Handles validation errors from a BindingResult object.
     * If there are errors, it collects these errors into a map and returns a ResponseEntity with a bad request status and the error map as the body.
     * If there are no errors, it returns null.
     *
     * @param bindingResult the BindingResult object containing the validation errors
     * @return a ResponseEntity with a bad request status and the error map as the body if there are errors, null otherwise
     */
    public static ResponseEntity<Object> handleBindingResultErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        return null;
    }

}
