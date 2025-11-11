package com.ecommerce.be.Dto;

import com.ecommerce.be.Entity.CartItem;
import com.ecommerce.be.Entity.Product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "CartProductDTO", description = "Product information which is stored in CartInfomationDTO")
public class CartProductDTO {
    private Integer productId;
    private String name;       
    private String coverImage;
    private Integer quantity;
    private String note;
    private Integer discount;
    private Integer price;
    private String sellerAvatar;
    private String sellerUsername;
    private String sellerName;

    public CartProductDTO(Product product, CartItem item) {
        this.productId = product.getProductId();
        this.name = product.getName();
        this.coverImage = product.getCoverImage();
        this.quantity = item.getQuantity();
        this.note = item.getNote();
        this.discount = product.getDiscount();
        this.price = product.getPrice();
        this.sellerAvatar = product.getSeller().getAvatar();
        this.sellerUsername = product.getSeller().getUsername();
        this.sellerName = product.getSeller().getName();
    }
}
