package com.example.shop.service.interfaces;

import com.example.shop.model.bind.SubscriberAddBindingModel;
import com.example.shop.model.bind.SubscriberUpdateBindingModel;
import com.example.shop.model.view.SubscriberViewModel;

import java.util.List;

/**
 * Service interface for managing subscribers.
 * This interface provides methods for adding, retrieving, updating, and deleting subscribers.
 */
public interface SubscriberService {
    SubscriberViewModel addSubscriber(SubscriberAddBindingModel subscriberAddBindingModel);

    SubscriberViewModel getSubscriber(Long id);

    List<SubscriberViewModel> getAllSubscribers();

    SubscriberViewModel updateSubscriber(Long id, SubscriberUpdateBindingModel subscriberUpdateBindingModel);

    SubscriberViewModel deleteSubscriber(Long id);

    String addProductToSubscriber(Long subscriberId, Long productId);
}
