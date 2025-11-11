package com.ecommerce.be.Repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.be.Constant.OrderStatus;
import com.ecommerce.be.Entity.Order;

import io.lettuce.core.dynamic.annotation.Param;

import com.ecommerce.be.Dto.OrderBuyerQueryResult;
import com.ecommerce.be.Dto.OrderDetailSellerQueryResult;
import com.ecommerce.be.Dto.OrderForSellerPageDTO;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByCommonId(String commonId);

    @Modifying(clearAutomatically = true)
    @Query(value = """
        UPDATE Order o SET o.status = :status WHERE o.commonId = :commonId
    """)
    public Integer updateStatus(String commonId, OrderStatus status);


    @Query(value = """
        SELECT new com.bkartisan.be.Dto.OrderBuyerQueryResult(o.orderId, o.status, o.totalPrice, o.shipPrice, o.discountPrice, o.createAt, o.paymentMethod, 
                        s.username, s.name, s.avatar, 
                        p.coverImage, p.name, op.quantity, op.quantity * p.price, p.discount)
        FROM Order o
        JOIN User s ON s.username = o.seller
        JOIN OrderProduct op ON op.orderId = o.orderId
        JOIN Product p ON p.productId = op.productId
        WHERE o.buyer = :buyer AND 
            (:status IS NULL OR o.status = :status)
        ORDER BY o.orderId DESC
    """)
    public List<OrderBuyerQueryResult> findByBuyerAndStatusOrderById(@Param("buyer") String buyer, @Param("status") OrderStatus status);

    @Query("""
            SELECT new com.bkartisan.be.Dto.OrderForSellerPageDTO(o.orderId, o.seller, o.createAt, o.status, o.paymentMethod, o.hasGift, o.totalPrice, :seller)
            FROM Order o
            WHERE o.seller = :seller
            ORDER BY o.createAt DESC
    """)
    public List<OrderForSellerPageDTO> findOrderForSellerPage(@Param("seller") String seller, Pageable pageable);

    @Query(value = """
        SELECT new com.bkartisan.be.Dto.OrderDetailSellerQueryResult(o.orderId, o.status, o.totalPrice, o.shipPrice, o.discountPrice, o.hasGift, o.isReturn, 
                        s.name, s.username, s.numPhone, s.email, s.address, s.avatar,
                        p.productId, p.name, op.quantity, p.price)
        FROM Order o
        JOIN User s ON s.username = o.buyer
        JOIN OrderProduct op ON op.orderId = o.orderId
        JOIN Product p ON p.productId = op.productId
        WHERE o.seller = :seller AND o.orderId = :orderId
    """)
    public List<OrderDetailSellerQueryResult> getOrderDetailForSeller(@Param("seller") String seller, @Param("orderId") String orderId);
}
