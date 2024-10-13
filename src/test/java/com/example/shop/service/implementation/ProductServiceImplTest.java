package com.example.shop.service.implementation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.shop.model.bind.ProductAddBindingModel;
import com.example.shop.model.bind.ProductUpdateBindingModel;
import com.example.shop.model.entity.Product;
import com.example.shop.model.view.ProductViewModel;
import com.example.shop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Unit tests for the {@link ProductServiceImpl} class.
 *
 * This test class verifies the behavior of methods in {@link ProductServiceImpl},
 * using mock objects for the dependencies such as {@link ProductRepository} and {@link ModelMapper}.
 *
 */
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    private Product product;
    private ProductAddBindingModel productAddBindingModel;
    private ProductViewModel productViewModel;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        productAddBindingModel = new ProductAddBindingModel();
        productAddBindingModel.setName("Test Product");

        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setCreationDate(LocalDateTime.now());
        product.setSubscribers(new ArrayList<>());

        productViewModel = new ProductViewModel();
        productViewModel.setId(1L);
        productViewModel.setName("Test Product");
        productViewModel.setSubscribers(new ArrayList<>());
    }

    /**
     * Tests adding a new product and verifies that it is saved and mapped correctly.
     */
    @Test
    public void testAddProduct() {
        when(modelMapper.map(productAddBindingModel, Product.class)).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(modelMapper.map(product, ProductViewModel.class)).thenReturn(productViewModel);

        ProductViewModel result = productServiceImpl.addProduct(productAddBindingModel);

        assertNotNull(result);
        assertEquals(productViewModel.getId(), result.getId());
        assertEquals(productViewModel.getName(), result.getName());

        verify(modelMapper).map(productAddBindingModel, Product.class);
        verify(productRepository).save(product);
        verify(modelMapper).map(product, ProductViewModel.class);
    }

    /**
     * Tests retrieving a product by its ID when it exists.
     */
    @Test
    public void testGetProductWhenProductExists() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(modelMapper.map(product, ProductViewModel.class)).thenReturn(productViewModel);

        ProductViewModel result = productServiceImpl.getProduct(1L);

        assertNotNull(result);
        assertEquals(productViewModel.getId(), result.getId());
        assertEquals(productViewModel.getName(), result.getName());

        verify(productRepository).findById(1L);
        verify(modelMapper).map(product, ProductViewModel.class);
    }

    /**
     * Tests retrieving a product by its ID when it does not exist.
     */
    @Test
    public void testGetProductWhenProductDoesNotExist() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        ProductViewModel result = productServiceImpl.getProduct(1L);

        assertNull(result);

        verify(productRepository).findById(1L);
        verify(modelMapper, never()).map(any(Product.class), eq(ProductViewModel.class));
    }

    /**
     * Tests retrieving all products and verifies the list returned is correctly mapped.
     */
    @Test
    public void testGetAllProducts() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(product));
        when(modelMapper.map(product, ProductViewModel.class)).thenReturn(productViewModel);

        List<ProductViewModel> result = productServiceImpl.getAllProducts();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(productViewModel.getId(), result.get(0).getId());

        verify(productRepository).findAll();
        verify(modelMapper).map(product, ProductViewModel.class);
    }

    /**
     * Tests retrieving all sold products and verifies the list returned is correctly mapped.
     */
    @Test
    public void testGetAllSoldProducts() {
        when(productRepository.findAllWithAtLeastOneSubscriber()).thenReturn(Arrays.asList(product));
        when(modelMapper.map(product, ProductViewModel.class)).thenReturn(productViewModel);

        List<ProductViewModel> result = productServiceImpl.getAllSoldProducts();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(productViewModel.getId(), result.get(0).getId());

        verify(productRepository).findAllWithAtLeastOneSubscriber();
        verify(modelMapper).map(product, ProductViewModel.class);
    }

    /**
     * Tests retrieving all active products and verifies the list returned is correctly mapped.
     */
    @Test
    public void testGetAllActiveProducts() {
        when(productRepository.findByIsUnderSaleTrue()).thenReturn(Arrays.asList(product));
        when(modelMapper.map(product, ProductViewModel.class)).thenReturn(productViewModel);

        List<ProductViewModel> result = productServiceImpl.getAllActiveProducts();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(productViewModel.getId(), result.get(0).getId());

        verify(productRepository).findByIsUnderSaleTrue();
        verify(modelMapper).map(product, ProductViewModel.class);
    }

    /**
     * Tests retrieving products by popularity and verifies the list returned is correctly mapped.
     */
    @Test
    public void testGetAllProductsByPopularity() {
        when(productRepository.findAllOrderBySubscribersDesc()).thenReturn(Arrays.asList(product));
        when(modelMapper.map(product, ProductViewModel.class)).thenReturn(productViewModel);

        List<ProductViewModel> result = productServiceImpl.getAllProductsByPopularity();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(productViewModel.getId(), result.get(0).getId());

        verify(productRepository).findAllOrderBySubscribersDesc();
        verify(modelMapper).map(product, ProductViewModel.class);
    }

    /**
     * Tests retrieving products by creation date range and verifies the list returned is correctly mapped.
     */
    @Test
    public void testGetProductsByCreationDateBetween() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now();

        when(productRepository.findByCreationDateBetween(startDate, endDate)).thenReturn(Arrays.asList(product));
        when(modelMapper.map(product, ProductViewModel.class)).thenReturn(productViewModel);

        List<ProductViewModel> result = productServiceImpl.getProductsByCreationDateBetween(startDate, endDate);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(productViewModel.getId(), result.get(0).getId());

        verify(productRepository).findByCreationDateBetween(startDate, endDate);
        verify(modelMapper).map(product, ProductViewModel.class);
    }

    /**
     * Tests updating a product and verifies that it is updated and mapped correctly.
     */
    @Test
    public void testUpdateProductWhenProductExists() {
        ProductUpdateBindingModel productUpdateBindingModel = new ProductUpdateBindingModel();
        productUpdateBindingModel.setName("Updated Product");
        productUpdateBindingModel.setIsUnderSale(true);

        Product updatedProduct = new Product();
        updatedProduct.setId(1L);
        updatedProduct.setName("Updated Product");
        updatedProduct.setUnderSale(true);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);
        when(modelMapper.map(updatedProduct, ProductViewModel.class)).thenReturn(productViewModel);

        ProductViewModel result = productServiceImpl.updateProduct(1L, productUpdateBindingModel);

        assertNotNull(result);
        assertEquals(productViewModel.getId(), result.getId());

        verify(productRepository).findById(1L);
        verify(productRepository).save(product);
        verify(modelMapper).map(updatedProduct, ProductViewModel.class);
    }

    /**
     * Tests updating a product that does not exist.
     */
    @Test
    public void testUpdateProductWhenNotExists() {
        ProductUpdateBindingModel productUpdateBindingModel = new ProductUpdateBindingModel();

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        ProductViewModel result = productServiceImpl.updateProduct(1L, productUpdateBindingModel);

        assertNull(result);
        verify(productRepository).findById(1L);
        verify(productRepository, never()).save(any(Product.class));
    }

    /**
     * Tests deleting a product and verifies that it is deleted and the correct view model is returned.
     */
    @Test
    public void testDeleteProductWhenExists() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(modelMapper.map(product, ProductViewModel.class)).thenReturn(productViewModel);

        ProductViewModel result = productServiceImpl.deleteProduct(1L);

        assertNotNull(result);
        verify(productRepository).findById(1L);
        verify(productRepository).deleteById(1L);
    }

    /**
     * Tests deleting a product that does not exist.
     */
    @Test
    public void testDeleteProductWhenNotExists() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        ProductViewModel result = productServiceImpl.deleteProduct(1L);

        assertNull(result);
        verify(productRepository).findById(1L);
        verify(productRepository, never()).deleteById(any(Long.class));
    }

}
