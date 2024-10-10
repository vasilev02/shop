package com.example.shop.controler;

import com.example.shop.model.bind.SubscriberAddBindingModel;
import com.example.shop.model.bind.SubscriberUpdateBindingModel;
import com.example.shop.model.view.SubscriberViewModel;
import com.example.shop.service.implementation.SubscriberServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller for managing subscribers.
 * This controller provides endpoints for adding, retrieving, updating, and deleting subscribers.
 */
@RestController
@RequestMapping("/api/subscribers")
public class SubscriberController {

    private final SubscriberServiceImpl subscriberService;

    /**
     * REST controller for managing subscribers.
     * This controller provides endpoints for adding, retrieving, updating, and deleting subscribers.
     */
    @Autowired
    public SubscriberController(SubscriberServiceImpl subscriberService) {
        this.subscriberService = subscriberService;
    }

    /**
     * Adds a new subscriber.
     *
     * @param subscriberAddBindingModel the subscriber data
     * @param bindingResult             the object that holds the result of the validation of the subscriber data
     * @return a ResponseEntity with the created subscriber and a status of 201 if successful, or a ResponseEntity with validation errors and a status of 400 if not
     */
    @PostMapping
    public ResponseEntity<Object> addSubscriber(@Valid @RequestBody SubscriberAddBindingModel subscriberAddBindingModel, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        SubscriberViewModel subscriberViewModel = this.subscriberService.addSubscriber(subscriberAddBindingModel);
        return ResponseEntity.status(201).body(subscriberViewModel);
    }

    /**
     * Retrieves a subscriber by its id.
     *
     * @param id the id of the subscriber to retrieve
     * @return a ResponseEntity with the retrieved subscriber and a status of 200 if successful, or a ResponseEntity with an error message and a status of 400 if not
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getSubscriber(@PathVariable Long id) {
        SubscriberViewModel subscriber = this.subscriberService.getSubscriber(id);
        if (subscriber != null) {
            return ResponseEntity.status(200).body(subscriber);
        }
        return ResponseEntity.badRequest().body(String.format(ControllerUtils.SUBSCRIBER_NOT_FOUND, id));
    }

    /**
     * Retrieves all subscribers.
     *
     * @return a list of all subscribers
     */
    @GetMapping
    public List<SubscriberViewModel> getAllSubscribers() {
        return this.subscriberService.getAllSubscribers();
    }

    /**
     * Updates a subscriber by its id.
     *
     * @param id                           the id of the subscriber to update
     * @param subscriberUpdateBindingModel the new subscriber data
     * @param bindingResult                the object that holds the result of the validation of the new subscriber data
     * @return a ResponseEntity with the updated subscriber and a status of 201 if successful, or a ResponseEntity with validation errors or an error message and a status of 400 if not
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSubscriber(@PathVariable Long id, @Valid @RequestBody SubscriberUpdateBindingModel subscriberUpdateBindingModel, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        SubscriberViewModel subscriberViewModel = this.subscriberService.updateSubscriber(id, subscriberUpdateBindingModel);
        if (subscriberViewModel != null) {
            return ResponseEntity.status(201).body(subscriberViewModel);
        }
        return ResponseEntity.badRequest().body(String.format(ControllerUtils.SUBSCRIBER_NOT_FOUND, id));
    }

    /**
     * Deletes a subscriber by its id.
     *
     * @param id the id of the subscriber to delete
     * @return a ResponseEntity with the deleted subscriber and a status of 200 if successful, or a ResponseEntity with an error message and a status of 400 if not
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSubscriber(@PathVariable Long id) {
        SubscriberViewModel subscriberViewModel = this.subscriberService.deleteSubscriber(id);
        if (subscriberViewModel != null) {
            return ResponseEntity.status(200).body(subscriberViewModel);
        }
        return ResponseEntity.badRequest().body(String.format(ControllerUtils.SUBSCRIBER_NOT_FOUND, id));
    }

    /**
     * Retrieves the total count of subscribers.
     *
     * @return a ResponseEntity with the total count of subscribers and a status of 200
     */
    @GetMapping("/total")
    public ResponseEntity<Object> getAllSubscribersTotalCount() {
        return ResponseEntity.status(200).body(this.subscriberService.getAllSubscribers().size() + " subscribers in the database.");
    }

    /**
     * Adds a product to a subscriber.
     *
     * @param subscriberId the id of the subscriber
     * @param productId    the id of the product to add
     * @return a ResponseEntity with a success message and a status of 201 if successful, or a ResponseEntity with an error message and a status of 400 if not
     */
    @PostMapping("/{subscriberId}/products/{productId}")
    public ResponseEntity<Object> addProductToSubscriber(@PathVariable Long subscriberId, @PathVariable Long productId) {
        String response = this.subscriberService.addProductToSubscriber(subscriberId, productId);
        return ResponseEntity.status(201).body(response);
    }

}
