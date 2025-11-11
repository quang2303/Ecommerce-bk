package com.ecommerce.be.Service;

import org.springframework.stereotype.Service;

import com.ecommerce.be.Entity.Order;
import com.ecommerce.be.Entity.OrderProduct;
import com.ecommerce.be.Repository.OrderProductRepository;

@Service
public class OrderProductService {
    
    private OrderProductRepository orderProductRepository;

    public OrderProductService(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

    public OrderProduct saveOrderProduct(Order order, Integer productId, Integer quantity, String buyer, String note) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setOrderId(order.getOrderId());
        orderProduct.setProductId(productId);
        orderProduct.setQuantity(quantity);
        orderProduct.setBuyer(buyer);
        orderProduct.setNote(note);
        return orderProductRepository.save(orderProduct);
    }

}
