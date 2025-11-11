package com.ecommerce.be.Entity;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "carts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Cart stored in database. Act like a order_product table in database")
public class OrderProduct implements Serializable {
    @Id
    @Column()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Integer productId;
    @Column
    private Integer quantity;
    @Column
    private String note;
    @Column(nullable = true, unique = true)
    private String buyer;
    @Column
    private Integer discountId;
    @Column(length = 10)
    private String orderId;
}
