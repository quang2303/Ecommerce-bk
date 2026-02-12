package com.ecommerce.be.Repository;

import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.be.Entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>, ProductRepositoryCustom {

    /**
     * Find products by name and category.
     * If one of them is null, the condition with respect to it will be ignored by the IS NULL clause.
     */
    @Query("SELECT p FROM Product p WHERE "
            + "(:category IS NULL OR p.category = :category) "
            + "AND (:searchTerm IS NULL OR p.name LIKE %:searchTerm%)")
    List<Product> findByNameAndCategory(String searchTerm, Integer category, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.quantity = p.quantity - :amount WHERE p.productId = :id AND p.quantity >= :amount")
    int decreaseStock(@Param("id") Integer id, @Param("amount") Integer amount);
}