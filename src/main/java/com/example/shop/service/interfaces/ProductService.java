package com.example.shop.service.interfaces;

import com.example.shop.model.bind.ProductAddBindingModel;
import com.example.shop.model.bind.ProductUpdateBindingModel;
import com.example.shop.model.view.ProductViewModel;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service interface for managing products.
 * This interface provides methods for adding, retrieving, updating, and deleting products.
 */
public interface ProductService {
    ProductViewModel addProduct(ProductAddBindingModel productAddBindingModel);

    ProductViewModel getProduct(Long id);

    List<ProductViewModel> getAllProducts();

    ProductViewModel updateProduct(Long id, ProductUpdateBindingModel productUpdateBindingModel);

    ProductViewModel deleteProduct(Long id);

    List<ProductViewModel> getAllSoldProducts();

    List<ProductViewModel> getAllActiveProducts();

    List<ProductViewModel> getProductsByCreationDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
