package com.ecommerce.be.Service;

import java.time.Duration;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;


import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.ecommerce.be.Dto.CartInformationDTO;
import com.ecommerce.be.Dto.CartProductDTO;
import com.ecommerce.be.Entity.CartItem;
import com.ecommerce.be.Entity.Product;

@Service
public class CartService {

    private String ID_PREFIX = "cart:";

    private RedisTemplate<String, Object> redisOperations;
    private HashOperations<String, Integer, CartItem> hashOperations;
    private ProductService productService;

    public CartService(RedisTemplate<String, Object> redisOperations, ProductService productService) {
        this.productService = productService;
        this.redisOperations = redisOperations;
        this.hashOperations = redisOperations.opsForHash();
    }

    // TODO: Handle NumberFormatException when parseInt() fails.
    // TODO: Add discount to cart.
    public CartInformationDTO getCart(String username) {
        String key = ID_PREFIX + username;
        CartInformationDTO cartInfo = new CartInformationDTO();

        Set<Entry<Integer, CartItem>> entries = hashOperations.entries(key).entrySet();

        try {
            for (Entry<Integer,CartItem> entry : entries) {
                Integer productId = entry.getKey();
                CartItem item = entry.getValue();
                Product product = productService.getProduct(productId);
    
                cartInfo.setTotalPrice(cartInfo.getTotalPrice() + item.getQuantity() * product.getPrice());
                cartInfo.setNumberOfItems(cartInfo.getNumberOfItems() + item.getQuantity());
                cartInfo.getItems().add(new CartProductDTO(product, item));
            }
    
            return cartInfo;
        } catch (NumberFormatException e) {
            System.out.println("Error in getCart: " + e.getMessage());
            throw e;
        }
    }

    public Integer getTotalPrice(String username) {
        String key = ID_PREFIX + username;
        // No items in cart - return 0
        if (!redisOperations.hasKey(key)) {
            return 0;
        }

        try {
            Map<Integer, CartItem> entries = hashOperations.entries(key);

            // First, map to a stream of price of each item. Then sum it up with
            // Collectors.summingInt.
            Integer totalPrice = entries.entrySet().stream().map(e -> {
                Product prod = productService.getProduct(Integer.parseInt(e.getKey().toString()));
                Integer price = prod.getPrice();

                CartItem item = e.getValue();
                Integer quantity = item.getQuantity();

                return price * quantity;
            }).collect(Collectors.summingInt(Integer::intValue));

            return totalPrice;
        } catch (NumberFormatException e) {
            System.out.println("Error in getTotalPrice: " + e.getMessage());
            throw e;
        }
    }

    public Integer getTotalItems(String username) {
        String key = ID_PREFIX + username;
        return hashOperations.entries(key).values().stream()
                .collect(Collectors.summingInt(e -> e.getQuantity()));
    }

    public void addProductToCart(String username, Integer productID, Integer quantity) {
        String key = ID_PREFIX + username;

        // If the user cart haven't existed in redis yet, then create a new one and set expire time to 7 days
        if (!redisOperations.hasKey(key)) {
            hashOperations.put(key, productID, new CartItem("", quantity));
            redisOperations.expire(key, Duration.ofDays(7));
        }
        // If the cart exist and the product is in cart, then increment the quantity
        else if (hashOperations.hasKey(key, productID)) {
            CartItem currentItem = hashOperations.get(key, productID);
            currentItem.setQuantity(currentItem.getQuantity() + quantity);
            hashOperations.put(key, productID, currentItem);
        }
        // If the cart exist and the product isn't in cart, then put product into cart
        else {
            CartItem newItem = new CartItem("", quantity);
            hashOperations.put(key, productID, newItem);
        }
    }

    public void removeProductFromCart(String username, Integer productID) {
        String key = ID_PREFIX + username;
        hashOperations.delete(key, productID);
    }

    public void updateProductOnCart(String username, Integer productID, Integer quantity, String note) {
        String key = ID_PREFIX + username;
        hashOperations.put(key, productID, new CartItem(note, quantity));
    }

    public Map<String, List<CartProductDTO>> getCartProductsToSellerMap(String username) {
        CartInformationDTO cartInfo = getCart(username);
        List<CartProductDTO> productsInCart = cartInfo.getItems();

        // Map products into respective seller
        Map<String, List<CartProductDTO>> sellerProductsMap = new HashMap<>();
        for (CartProductDTO product : productsInCart) {
            if (!sellerProductsMap.containsKey(product.getSellerUsername())) {
                sellerProductsMap.put(product.getSellerUsername(), new ArrayList<CartProductDTO>());
            }
            sellerProductsMap.get(product.getSellerUsername()).add(product);
        }

        return sellerProductsMap;
    }

    Boolean clearCart(String username) {
        String key = ID_PREFIX + username;
        return redisOperations.delete(key);
    }
}