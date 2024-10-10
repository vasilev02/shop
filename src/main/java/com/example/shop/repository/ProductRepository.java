package com.example.shop.repository;

import com.example.shop.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for the Product entity.
 * This interface provides CRUD operations for the Product entity.
 * It extends JpaRepository which provides JPA related methods like save, findById, findAll, etc.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Finds all products where isUnderSale is true.
     *
     * @return a list of products where isUnderSale is true
     */
    List<Product> findByIsUnderSaleTrue();

    /**
     * Finds all products that have at least one subscriber.
     *
     * @return a list of products that have at least one subscriber
     */
    @Query("SELECT p FROM Product p WHERE SIZE(p.subscribers) > 0")
    List<Product> findAllWithAtLeastOneSubscriber();

    /**
     * Finds all products ordered by the number of subscribers in descending order.
     *
     * @return a list of products ordered by the number of subscribers in descending order
     */
    @Query("SELECT p FROM Product p ORDER BY SIZE(p.subscribers) DESC")
    List<Product> findAllOrderBySubscribersDesc();

    /**
     * Finds all products with a creation date within a given range.
     *
     * @param startDate the start date of the range
     * @param endDate the end date of the range
     * @return a list of products with a creation date within the given range
     */
    List<Product> findByCreationDateBetween(LocalDateTime startDate, LocalDateTime endDate);

}
