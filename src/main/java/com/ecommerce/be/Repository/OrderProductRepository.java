package com.ecommerce.be.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.be.Entity.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer> {
    
}
