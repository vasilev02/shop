package com.example.shop.service.implementation;

import com.example.shop.controler.ControllerUtils;
import com.example.shop.model.bind.SubscriberAddBindingModel;
import com.example.shop.model.bind.SubscriberUpdateBindingModel;
import com.example.shop.model.entity.Product;
import com.example.shop.model.entity.Subscriber;
import com.example.shop.model.view.ProductViewModel;
import com.example.shop.model.view.SubscriberViewModel;
import com.example.shop.repository.ProductRepository;
import com.example.shop.repository.SubscriberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link SubscriberServiceImpl} class.
 *
 * This test class verifies the behavior of methods in {@link SubscriberServiceImpl},
 * using mock objects for the dependencies such as {@link SubscriberRepository} and {@link ModelMapper}.
 *
 */
public class SubscriberServiceImplTest {

    @Mock
    private SubscriberRepository subscriberRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SubscriberServiceImpl subscriberServiceImpl;

    private Subscriber subscriber;
    private SubscriberViewModel subscriberViewModel;
    private Product product;
    private ProductViewModel productViewModel;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        subscriber = new Subscriber();
        subscriber.setId(1L);
        subscriber.setFirstName("John");
        subscriber.setLastName("Doe");
        subscriber.setJoinedDate(LocalDateTime.now());
        subscriber.setProducts(new ArrayList<>());

        subscriberViewModel = new SubscriberViewModel();
        subscriberViewModel.setId(1L);
        subscriberViewModel.setFirstName("John");
        subscriberViewModel.setLastName("Doe");
        subscriberViewModel.setProducts(new ArrayList<>());

        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setUnderSale(true);
        product.setSubscribers(new ArrayList<>());

        productViewModel = new ProductViewModel();
        productViewModel.setId(1L);
        productViewModel.setName("Test Product");
        productViewModel.setSubscribers(new ArrayList<>());
    }

    /**
     * Tests the addSubscriber() method.
     */
    @Test
    public void testAddSubscriber() {
        SubscriberAddBindingModel subscriberAddBindingModel = new SubscriberAddBindingModel();
        subscriberAddBindingModel.setFirstName("John");
        subscriberAddBindingModel.setLastName("Doe");

        when(modelMapper.map(subscriberAddBindingModel, Subscriber.class)).thenReturn(subscriber);
        when(subscriberRepository.save(any(Subscriber.class))).thenReturn(subscriber);
        when(modelMapper.map(subscriber, SubscriberViewModel.class)).thenReturn(subscriberViewModel);

        SubscriberViewModel result = subscriberServiceImpl.addSubscriber(subscriberAddBindingModel);

        assertNotNull(result);
        assertEquals(subscriberViewModel.getFirstName(), result.getFirstName());
        verify(subscriberRepository).save(any(Subscriber.class));
        verify(modelMapper).map(subscriberAddBindingModel, Subscriber.class);
        verify(modelMapper).map(subscriber, SubscriberViewModel.class);
    }

    /**
     * Tests the getSubscriber() method when the subscriber exists.
     */
    @Test
    public void testGetSubscriberWhenExists() {
        when(subscriberRepository.findById(1L)).thenReturn(Optional.of(subscriber));
        when(modelMapper.map(subscriber, SubscriberViewModel.class)).thenReturn(subscriberViewModel);

        SubscriberViewModel result = subscriberServiceImpl.getSubscriber(1L);

        assertNotNull(result);
        assertEquals(subscriberViewModel.getId(), result.getId());
        verify(subscriberRepository).findById(1L);
        verify(modelMapper).map(subscriber, SubscriberViewModel.class);
    }

    /**
     * Tests the getSubscriber() method when the subscriber does not exist.
     */
    @Test
    public void testGetSubscriberWhenNotExists() {
        when(subscriberRepository.findById(1L)).thenReturn(Optional.empty());

        SubscriberViewModel result = subscriberServiceImpl.getSubscriber(1L);

        assertNull(result);
        verify(subscriberRepository).findById(1L);
        verify(modelMapper, never()).map(any(Subscriber.class), eq(SubscriberViewModel.class));
    }

    /**
     * Tests the getAllSubscribers() method.
     */
    @Test
    public void testGetAllSubscribers() {
        List<Subscriber> subscribers = List.of(subscriber);

        when(subscriberRepository.findAll()).thenReturn(subscribers);
        when(modelMapper.map(subscriber, SubscriberViewModel.class)).thenReturn(subscriberViewModel);

        List<SubscriberViewModel> result = subscriberServiceImpl.getAllSubscribers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(subscriberViewModel.getId(), result.get(0).getId());
        verify(subscriberRepository).findAll();
        verify(modelMapper).map(subscriber, SubscriberViewModel.class);
    }

    /**
     * Tests the updateSubscriber() method when the subscriber exists.
     */
    @Test
    public void testUpdateSubscriberWhenExists() {
        SubscriberUpdateBindingModel subscriberUpdateBindingModel = new SubscriberUpdateBindingModel();
        subscriberUpdateBindingModel.setFirstName("Updated Name");
        subscriberUpdateBindingModel.setLastName("Updated Last Name");

        when(subscriberRepository.findById(1L)).thenReturn(Optional.of(subscriber));
        when(subscriberRepository.save(any(Subscriber.class))).thenReturn(subscriber);
        when(modelMapper.map(subscriber, SubscriberViewModel.class)).thenReturn(subscriberViewModel);

        SubscriberViewModel result = subscriberServiceImpl.updateSubscriber(1L, subscriberUpdateBindingModel);

        assertNotNull(result);
        verify(subscriberRepository).findById(1L);
        verify(subscriberRepository).save(subscriber);
        verify(modelMapper).map(subscriber, SubscriberViewModel.class);
    }

    /**
     * Tests the deleteSubscriber() method when the subscriber exists.
     */
    @Test
    public void testDeleteSubscriberWhenExists() {
        when(subscriberRepository.findById(1L)).thenReturn(Optional.of(subscriber));
        when(modelMapper.map(subscriber, SubscriberViewModel.class)).thenReturn(subscriberViewModel);

        SubscriberViewModel result = subscriberServiceImpl.deleteSubscriber(1L);

        assertNotNull(result);
        verify(subscriberRepository).findById(1L);
        verify(subscriberRepository).deleteById(1L);
        verify(modelMapper).map(subscriber, SubscriberViewModel.class);
    }

    /**
     * Tests the deleteSubscriber() method when the subscriber does not exist.
     */
    @Test
    public void testDeleteSubscriberWhenNotExists() {
        when(subscriberRepository.findById(1L)).thenReturn(Optional.empty());

        SubscriberViewModel result = subscriberServiceImpl.deleteSubscriber(1L);

        assertNull(result);
        verify(subscriberRepository).findById(1L);
        verify(subscriberRepository, never()).deleteById(anyLong());
        verify(modelMapper, never()).map(any(Subscriber.class), eq(SubscriberViewModel.class));
    }

    /**
     * Tests the addProductToSubscriber() method when subscriber and product both exist and product is under sale.
     */
    @Test
    public void testAddProductToSubscriberSuccess() {
        when(subscriberRepository.findById(1L)).thenReturn(Optional.of(subscriber));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(subscriberRepository.save(any(Subscriber.class))).thenReturn(subscriber);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Object result = subscriberServiceImpl.addProductToSubscriber(1L, 1L);

        assertNotNull(result);
        assertTrue(result instanceof Subscriber);
        verify(subscriberRepository).findById(1L);
        verify(productRepository).findById(1L);
        verify(subscriberRepository).save(subscriber);
        verify(productRepository).save(product);
    }

    /**
     * Tests the addProductToSubscriber() method when the product is not under sale.
     */
    @Test
    public void testAddProductToSubscriberWhenProductNotUnderSale() {
        product.setUnderSale(false);
        when(subscriberRepository.findById(1L)).thenReturn(Optional.of(subscriber));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Object result = subscriberServiceImpl.addProductToSubscriber(1L, 1L);

        assertNotNull(result);
        assertEquals("Product Test Product is not under sale.", result);
        verify(subscriberRepository).findById(1L);
        verify(productRepository).findById(1L);
        verify(subscriberRepository, never()).save(any(Subscriber.class));
        verify(productRepository, never()).save(any(Product.class));
    }

    /**
     * Tests the addProductToSubscriber() method when subscriber does not exist.
     */
    @Test
    public void testAddProductToSubscriberWhenSubscriberNotFound() {
        when(subscriberRepository.findById(1L)).thenReturn(Optional.empty());

        Object result = subscriberServiceImpl.addProductToSubscriber(1L, 1L);

        assertNotNull(result);
        assertEquals(String.format(ControllerUtils.SUBSCRIBER_NOT_FOUND, 1L), result);
        verify(subscriberRepository).findById(1L);
        verify(subscriberRepository, never()).save(any(Subscriber.class));
        verify(productRepository, never()).save(any(Product.class));
    }

    /**
     * Tests the addProductToSubscriber() method when product does not exist.
     */
    @Test
    public void testAddProductToSubscriberWhenProductNotFound() {
        when(subscriberRepository.findById(1L)).thenReturn(Optional.of(subscriber));
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Object result = subscriberServiceImpl.addProductToSubscriber(1L, 1L);

        assertNotNull(result);
        assertEquals(String.format(ControllerUtils.PRODUCT_NOT_FOUND, 1L), result);
        verify(subscriberRepository).findById(1L);
        verify(productRepository).findById(1L);
        verify(subscriberRepository, never()).save(any(Subscriber.class));
        verify(productRepository, never()).save(any(Product.class));
    }



}
