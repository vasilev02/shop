package com.example.shop.controler;

import com.example.shop.model.bind.ProductAddBindingModel;
import com.example.shop.model.bind.ProductUpdateBindingModel;
import com.example.shop.model.view.ProductViewModel;
import com.example.shop.service.implementation.ProductServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller for managing products.
 * This controller provides endpoints for adding, retrieving, updating, and deleting products.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductServiceImpl productService;

    /**
     * Constructs a new ProductController with the given ProductService.
     *
     * @param productService the ProductService to use
     */
    @Autowired
    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    /**
     * Adds a new product.
     *
     * @param productAddBindingModel the product data
     * @param bindingResult          the object that holds the result of the validation of the product data
     * @return a ResponseEntity with the created product and a status of 201 if successful, or a ResponseEntity with validation errors and a status of 400 if not
     */
    @PostMapping
    public ResponseEntity<Object> addProduct(@Valid @RequestBody ProductAddBindingModel productAddBindingModel, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        ProductViewModel savedProductViewModel = this.productService.addProduct(productAddBindingModel);
        return ResponseEntity.status(201).body(savedProductViewModel);
    }

    /**
     * Retrieves a product by its id.
     *
     * @param id the id of the product to retrieve
     * @return a ResponseEntity with the retrieved product and a status of 200 if successful, or a ResponseEntity with an error message and a status of 400 if not
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getProduct(@PathVariable Long id) {
        ProductViewModel productViewModel = this.productService.getProduct(id);
        if (productViewModel != null) {
            return ResponseEntity.status(200).body(productViewModel);
        }
        return ResponseEntity.badRequest().body(String.format(ControllerUtils.PRODUCT_NOT_FOUND, id));
    }

    /**
     * Retrieves all products.
     *
     * @return a list of all products
     */
    @GetMapping
    public List<ProductViewModel> getAllProducts() {
        return this.productService.getAllProducts();
    }

    /**
     * Updates a product by its id.
     *
     * @param id                        the id of the product to update
     * @param productUpdateBindingModel the new product data
     * @param bindingResult             the object that holds the result of the validation of the new product data
     * @return a ResponseEntity with the updated product and a status of 201 if successful, or a ResponseEntity with validation errors or an error message and a status of 400 if not
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductUpdateBindingModel productUpdateBindingModel, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        ProductViewModel productViewModel = this.productService.updateProduct(id, productUpdateBindingModel);
        if (productViewModel != null) {
            return ResponseEntity.status(201).body(productViewModel);
        }
        return ResponseEntity.badRequest().body(String.format(ControllerUtils.PRODUCT_NOT_FOUND, id));
    }

    /**
     * Deletes a product by its id.
     *
     * @param id the id of the product to delete
     * @return a ResponseEntity with the deleted product and a status of 200 if successful, or a ResponseEntity with an error message and a status of 400 if not
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id) {
        ProductViewModel productViewModel = this.productService.deleteProduct(id);
        if (productViewModel != null) {
            return ResponseEntity.status(200).body(productViewModel);
        }
        return ResponseEntity.badRequest().body(String.format(ControllerUtils.PRODUCT_NOT_FOUND, id));
    }

    /**
     * Retrieves the total count of products.
     *
     * @return a ResponseEntity with the total count of products and a status of 200
     */
    @GetMapping("/total")
    public ResponseEntity<Object> getAllProductsTotalCount() {
        return ResponseEntity.status(200).body(this.productService.getAllProducts().size() + " products in the database.");
    }


    /**
     * Retrieves the total count of sold products.
     *
     * @return a ResponseEntity with the total count of sold products and a status of 200
     */
    @GetMapping("/total/sold")
    public ResponseEntity<Object> getAllSoldProductsTotalCount() {
        return ResponseEntity.status(200).body(this.productService.getAllSoldProducts().size() + " sold products.");
    }


    /**
     * Retrieves the total count of active products.
     *
     * @return a ResponseEntity with the total count of active products and a status of 200
     */
    @GetMapping("/total/active")
    public ResponseEntity<Object> getAllActiveProductsTotalCount() {
        return ResponseEntity.status(200).body(this.productService.getAllActiveProducts().size() + " active products.");
    }

    /**
     * Retrieves all products ordered by popularity.
     *
     * @return a ResponseEntity with all products ordered by popularity and a status of 200
     */
    @GetMapping("/total/popular")
    public ResponseEntity<Object> getAllProductsByPopularity() {
        return ResponseEntity.status(200).body(this.productService.getAllProductsByPopularity());
    }

    /**
     * Retrieves all products with a creation date within a given range.
     *
     * @param startDate the start date of the range
     * @param endDate   the end date of the range
     * @return a list of products with a creation date within the given range
     */
    @GetMapping("/date-range")
    public List<ProductViewModel> getProductsByCreationDateBetween(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        return productService.getProductsByCreationDateBetween(startDate, endDate);
    }

}
