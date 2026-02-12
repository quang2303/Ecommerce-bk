package com.ecommerce.be.Service;

import com.ecommerce.be.Repository.ProductRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.be.Constant.OrderStatus;
import com.ecommerce.be.Dto.CartProductDTO;
import com.ecommerce.be.Dto.CreatePaymentRequestDTO;
import com.ecommerce.be.Dto.OrderForBuyerPageDTO;
import com.ecommerce.be.Dto.OrderForSellerPageDTO;
import com.ecommerce.be.Dto.OrderBuyerQueryResult;
import com.ecommerce.be.Dto.OrderDetailForSellerPageDTO;
import com.ecommerce.be.Dto.OrderDetailSellerQueryResult;
import com.ecommerce.be.Entity.Order;
import com.ecommerce.be.Repository.OrderRepository;
import com.ecommerce.be.Util.PaymentUtil;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private OrderProductService orderProductService;
    private CartService cartService;
    private PaymentUtil paymentUtil;

    public OrderService(OrderRepository orderRepository, CartService cartService, PaymentUtil paymentUtil, 
            OrderProductService orderProductService, ProductRepository productRepository) {
        this.orderProductService = orderProductService;
        this.cartService = cartService;
        this.orderRepository = orderRepository;
        this.paymentUtil = paymentUtil;
        this.productRepository = productRepository;
    }

    public Order createAndSaveOrder(CreatePaymentRequestDTO createPaymentRequestDTO, String buyer, String seller,
            String commonId, Integer totalPrice) {
        String orderId = paymentUtil.getRandomNumber(10);

        Order order = Order.builder().orderId(orderId)
                .seller(seller)
                .paymentMethod(createPaymentRequestDTO.paymentMethod())
                .hasGift(false) // We haven't implement gift yet so set it to false
                .totalPrice(totalPrice)
                .status(OrderStatus.WAITING)
                .buyer(buyer)
                .buyerName(createPaymentRequestDTO.buyerName())
                .discountPrice(0) // We haven't implement discount yet so set it to 0
                .commonId(commonId)
                .nation(createPaymentRequestDTO.nation())
                .numPhone(createPaymentRequestDTO.numPhone())
                .address(createPaymentRequestDTO.address())
                .build();

        orderRepository.save(order);

        return order;
    }

    /**
     * Return commonId to used to set vnp_TxnRef in payment url.
     * 
     * @param createPaymentRequestDTO
     * @return commonId
     */
    @Transactional
    public String convertCartItemsToOrders(CreatePaymentRequestDTO createPaymentRequestDTO, String buyerUsername) {
        String commonId = paymentUtil.getRandomNumber(12);

        // Get Map of seller and products in buyer cart
        Map<String, List<CartProductDTO>> sellerProductsMap = cartService.getCartProductsToSellerMap(buyerUsername);

        // Convert the map into database
        sellerProductsMap.forEach((sellerUsername, products) -> {
            // Get total price
            Integer totalPrice = products.stream().mapToInt((product) -> product.getPrice() * product.getQuantity())
                    .sum();

            Order order = createAndSaveOrder(createPaymentRequestDTO, buyerUsername, sellerUsername, commonId, totalPrice);

            // Save each product in cart into OrderProduct
            products.forEach((product) -> {
                // Subtract inventory and check results
                int rowsUpdated = productRepository.decreaseStock(product.getProductId(), product.getQuantity());

                if (rowsUpdated == 0) {
                    // Through error for Transactional rollback
                    throw new RuntimeException("Sản phẩm " + product.getName() + " đã hết hàng hoặc không đủ số lượng!");
                }
                orderProductService.saveOrderProduct(order, product.getProductId(), product.getQuantity(), buyerUsername,
                        product.getNote());
            });
        });

        return commonId;
    }

    public List<OrderForBuyerPageDTO> getBuyerOrders(String username, OrderStatus status) {
        List<OrderBuyerQueryResult> orderQueryRes = orderRepository.findByBuyerAndStatusOrderById(username, status);
        List<OrderForBuyerPageDTO> orderBuyerDTOs = new ArrayList<>();

        // Convert OrderBuyerQueryResult to OrderBuyerDTO
        for (OrderBuyerQueryResult order : orderQueryRes) {
            if (orderBuyerDTOs.size() == 0 || !orderBuyerDTOs.get(orderBuyerDTOs.size() - 1).getOrderId().equals(order.getOrderId())) {
                orderBuyerDTOs.add(new OrderForBuyerPageDTO(order));
            }
            else {
                orderBuyerDTOs.get(orderBuyerDTOs.size() - 1).addItem(order);
            }
        }

        return orderBuyerDTOs;
    }

    public List<OrderForSellerPageDTO> getSellerOrders(String seller, Pageable pageable) {
        List<OrderForSellerPageDTO> orderForSellerPageDTOs = orderRepository.findOrderForSellerPage(seller, pageable);
        return orderForSellerPageDTOs;
    }

    public OrderDetailForSellerPageDTO getOrderDetailForSeller (String seller, String orderId) {
        List<OrderDetailSellerQueryResult> orderQueryRes = orderRepository.getOrderDetailForSeller(seller, orderId);
        OrderDetailForSellerPageDTO orderDetailForSellerPageDTO = null;

        for (OrderDetailSellerQueryResult order : orderQueryRes) {
            if (orderDetailForSellerPageDTO == null) {
                orderDetailForSellerPageDTO = new OrderDetailForSellerPageDTO(order);
            }
            else {
                orderDetailForSellerPageDTO.addItem(order);
            }
        }

        return orderDetailForSellerPageDTO;
    }
}
