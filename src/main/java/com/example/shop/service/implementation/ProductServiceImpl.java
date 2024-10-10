package com.example.shop.service.implementation;

import com.example.shop.model.bind.ProductAddBindingModel;
import com.example.shop.model.bind.ProductUpdateBindingModel;
import com.example.shop.model.entity.Product;
import com.example.shop.model.entity.Subscriber;
import com.example.shop.model.view.ProductViewModel;
import com.example.shop.repository.ProductRepository;
import com.example.shop.service.interfaces.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service implementation for managing products.
 * This class provides methods for adding, retrieving, updating, and deleting products.
 * It also provides methods for retrieving the total count of sold products, the total count of active products,
 * all products ordered by popularity, and all products with a creation date within a given range.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    /**
     * Constructs a new ProductServiceImpl with the given ProductRepository and ModelMapper.
     *
     * @param productRepository the ProductRepository to use
     * @param modelMapper       the ModelMapper to use
     */
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Adds a new product.
     *
     * @param productAddBindingModel the product data
     * @return the created product
     */
    public ProductViewModel addProduct(ProductAddBindingModel productAddBindingModel) {
        Product product = this.modelMapper.map(productAddBindingModel, Product.class);
        product.setCreationDate(LocalDateTime.now());
        product.setSubscribers(new ArrayList<>());

        Product savedProduct = this.productRepository.save(product);
        return this.modelMapper.map(savedProduct, ProductViewModel.class);
    }

    /**
     * Retrieves a product by its id.
     *
     * @param id the id of the product to retrieve
     * @return the retrieved product
     */
    public ProductViewModel getProduct(Long id) {
        Product product = this.checkIfProductExists(id);
        if (product != null) {
            return this.modelMapper.map(product, ProductViewModel.class);
        }
        return null;
    }

    /**
     * Retrieves all products.
     *
     * @return a list of all products
     */
    public List<ProductViewModel> getAllProducts() {
        return this.productRepository.findAll().stream().map(product -> this.modelMapper.map(product, ProductViewModel.class)).toList();
    }

    /**
     * Retrieves all sold products.
     *
     * @return a list of all sold products
     */
    public List<ProductViewModel> getAllSoldProducts() {
        return this.productRepository.findAllWithAtLeastOneSubscriber().stream()
                .map(product -> this.modelMapper.map(product, ProductViewModel.class)).toList();
    }

    /**
     * Retrieves all active products.
     *
     * @return a list of all active products
     */
    public List<ProductViewModel> getAllActiveProducts() {
        return this.productRepository.findByIsUnderSaleTrue().stream()
                .map(product -> this.modelMapper.map(product, ProductViewModel.class)).toList();
    }

    /**
     * Retrieves all products ordered by popularity.
     *
     * @return a list of all products ordered by popularity
     */
    public List<ProductViewModel> getAllProductsByPopularity() {
        return this.productRepository.findAllOrderBySubscribersDesc().stream()
                .map(product -> this.modelMapper.map(product, ProductViewModel.class)).toList();
    }

    /**
     * Retrieves all products with a creation date within a given range.
     *
     * @param startDate the start date of the range
     * @param endDate   the end date of the range
     * @return a list of products with a creation date within the given range
     */
    public List<ProductViewModel> getProductsByCreationDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return productRepository.findByCreationDateBetween(startDate, endDate).stream()
                .map(product -> modelMapper.map(product, ProductViewModel.class))
                .toList();
    }

    /**
     * Updates a product by its id.
     *
     * @param id                        the id of the product to update
     * @param productUpdateBindingModel the new product data
     * @return the updated product
     */
    public ProductViewModel updateProduct(Long id, ProductUpdateBindingModel productUpdateBindingModel) {
        Product existingProduct = this.checkIfProductExists(id);
        if (existingProduct != null) {
            existingProduct.setName(productUpdateBindingModel.getName());
            existingProduct.setUnderSale(productUpdateBindingModel.getUnderSale());

            Product updatedProduct = this.productRepository.save(existingProduct);
            return this.modelMapper.map(updatedProduct, ProductViewModel.class);
        }
        return null;
    }

    /**
     * Deletes a product by its id.
     *
     * @param id the id of the product to delete
     * @return the deleted product
     */
    public ProductViewModel deleteProduct(Long id) {
        Product product = this.checkIfProductExists(id);

        if (product != null) {

            List<Subscriber> subscribers = product.getSubscribers();

            for (Subscriber subscriber : subscribers) {
                List<Product> products = subscriber.getProducts();
                products.remove(product);
            }

            this.productRepository.deleteById(id);
            return this.modelMapper.map(product, ProductViewModel.class);
        }
        return null;
    }

    /**
     * Checks if a product exists by its id.
     *
     * @param id the id of the product to check
     * @return the product if it exists, null otherwise
     */
    private Product checkIfProductExists(Long id) {
        return this.productRepository.findById(id).orElse(null);
    }

}
