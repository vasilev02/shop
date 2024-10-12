package com.example.shop.service.implementation;

import com.example.shop.controler.ControllerUtils;
import com.example.shop.model.bind.SubscriberAddBindingModel;
import com.example.shop.model.bind.SubscriberUpdateBindingModel;

import com.example.shop.model.entity.Product;
import com.example.shop.model.entity.Subscriber;
import com.example.shop.model.view.SubscriberViewModel;
import com.example.shop.repository.ProductRepository;
import com.example.shop.repository.SubscriberRepository;
import com.example.shop.service.interfaces.SubscriberService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Service implementation for managing subscribers.
 * This class provides methods for adding, retrieving, updating, and deleting subscribers.
 * It also provides a method for adding a product to a subscriber.
 */
@Service
public class SubscriberServiceImpl implements SubscriberService {

    private final SubscriberRepository subscriberRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    /**
     * Constructs a new SubscriberServiceImpl with the given SubscriberRepository, ProductRepository, and ModelMapper.
     *
     * @param subscriberRepository the SubscriberRepository to use
     * @param productRepository    the ProductRepository to use
     * @param modelMapper          the ModelMapper to use
     */
    @Autowired
    public SubscriberServiceImpl(SubscriberRepository subscriberRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.subscriberRepository = subscriberRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Adds a new subscriber.
     *
     * @param subscriberAddBindingModel the subscriber data
     * @return the created subscriber
     */
    public SubscriberViewModel addSubscriber(SubscriberAddBindingModel subscriberAddBindingModel) {
        Subscriber subscriber = this.modelMapper.map(subscriberAddBindingModel, Subscriber.class);
        subscriber.setJoinedDate(LocalDateTime.now());
        subscriber.setProducts(new ArrayList<>());

        Subscriber savedSubscriber = this.subscriberRepository.save(subscriber);
        return this.modelMapper.map(savedSubscriber, SubscriberViewModel.class);
    }

    /**
     * Retrieves a subscriber by its id.
     *
     * @param id the id of the subscriber to retrieve
     * @return the retrieved subscriber
     */
    public SubscriberViewModel getSubscriber(Long id) {
        Subscriber subscriber = this.checkIfSubscriberExists(id);
        if (subscriber != null) {
            return this.modelMapper.map(subscriber, SubscriberViewModel.class);
        }
        return null;
    }

    /**
     * Retrieves all subscribers.
     *
     * @return a list of all subscribers
     */
    public List<SubscriberViewModel> getAllSubscribers() {
        return subscriberRepository.findAll().stream().map(subscriber -> this.modelMapper.map(subscriber, SubscriberViewModel.class)).toList();
    }

    /**
     * Updates a subscriber by its id.
     *
     * @param id                           the id of the subscriber to update
     * @param subscriberUpdateBindingModel the new subscriber data
     * @return the updated subscriber
     */
    public SubscriberViewModel updateSubscriber(Long id, SubscriberUpdateBindingModel subscriberUpdateBindingModel) {
        Subscriber existingSubscriber = this.checkIfSubscriberExists(id);
        if (existingSubscriber != null) {
            existingSubscriber.setFirstName(subscriberUpdateBindingModel.getFirstName());
            existingSubscriber.setLastName(subscriberUpdateBindingModel.getLastName());
            Subscriber updatedSubscriber = this.subscriberRepository.save(existingSubscriber);
            return this.modelMapper.map(updatedSubscriber, SubscriberViewModel.class);
        }
        return null;
    }

    /**
     * Deletes a subscriber by its id.
     *
     * @param id the id of the subscriber to delete
     * @return the deleted subscriber
     */
    public SubscriberViewModel deleteSubscriber(Long id) {
        Subscriber subscriber = this.checkIfSubscriberExists(id);
        if (subscriber != null) {
            SubscriberViewModel subscriberViewModel = this.modelMapper.map(subscriber, SubscriberViewModel.class);
            subscriber.setProducts(null);
            this.subscriberRepository.deleteById(id);
            return subscriberViewModel;
        }
        return null;
    }

    /**
     * Adds a product to a subscriber.
     *
     * @param subscriberId the id of the subscriber
     * @param productId    the id of the product to add
     * @return a subscriber object if the product was added successfully to it, or an error message otherwise
     */
    @Transactional
    public Object addProductToSubscriber(Long subscriberId, Long productId) {
        Subscriber subscriber = this.checkIfSubscriberExists(subscriberId);
        Optional<Product> product = this.productRepository.findById(productId);

        if (subscriber == null) {
            return String.format(ControllerUtils.SUBSCRIBER_NOT_FOUND, subscriberId);
        }
        if (product.isEmpty()) {
            return String.format(ControllerUtils.PRODUCT_NOT_FOUND, productId);
        }

        Product addProduct = product.get();

        if (!addProduct.isUnderSale()) {
            return String.format("Product %s is not under sale.", addProduct.getName());
        }

        List<Product> subscriberProducts = subscriber.getProducts();

        if (subscriberProducts.contains(addProduct)) {
            return String.format("Product %s is already assigned to Subscriber %s %s.", addProduct.getName(), subscriber.getFirstName(), subscriber.getLastName());
        }

        subscriberProducts.add(addProduct);
        subscriber.setProducts(subscriberProducts);
        Subscriber updatedSubscriber = this.subscriberRepository.save(subscriber);

        List<Subscriber> productSubscribers = addProduct.getSubscribers();

        if (productSubscribers.contains(subscriber)) {
            return String.format("Subscriber %s %s is already assigned to Product %s.", subscriber.getFirstName(), subscriber.getLastName(), addProduct.getName());
        }

        productSubscribers.add(subscriber);
        addProduct.setSubscribers(productSubscribers);
        this.productRepository.save(addProduct);

        return updatedSubscriber;
    }

    /**
     * Checks if a subscriber exists by its id.
     *
     * @param id the id of the subscriber to check
     * @return the subscriber if it exists, null otherwise
     */
    private Subscriber checkIfSubscriberExists(Long id) {
        return this.subscriberRepository.findById(id).orElse(null);
    }

}
