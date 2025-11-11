package com.ecommerce.be.Dto;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.ecommerce.be.Entity.Product;

@Service
public class ProductForSellerPageMapper implements Function<Product, ProductForSellerPageDTO> {
    @Override
    public ProductForSellerPageDTO apply(Product product) {
        return new ProductForSellerPageDTO(product.getProductId(), product.getPrice(), product.getName(),
                product.getSeller().getName(), product.getCoverImage(), product.getQuantity(), product.getStatus());
    }
}
