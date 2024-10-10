package com.example.shop.repository;

import com.example.shop.model.entity.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for the Subscriber entity.
 * This interface provides CRUD operations for the Subscriber entity.
 * It extends JpaRepository which provides JPA related methods like save, findById, findAll, etc.
 */
@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
}
