package com.ecommerce.be.Dto;

import com.ecommerce.be.Constant.ProductStatus;
import com.ecommerce.be.Entity.Category;
import com.ecommerce.be.Entity.Comment;
import com.ecommerce.be.Entity.Product;
import com.ecommerce.be.Entity.ProductLink;
import com.ecommerce.be.Entity.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class ProductDetailDTO {

    /**
     * Including the information of product image/video:
     * - link: the URL of asset.
     * - type: image or video.
     */
    @Getter
    private class ProductLinkDTO {
        String link;
        @Schema(description = "image or video")
        String type;

        ProductLinkDTO(ProductLink link) {
            this.link = link.getLink();
            this.type = link.getType();
        }
    }

    /**
     * Including the information of comment:
     * - data: the comment itself.
     * - replies: the replies of the comment.
     */
    @Getter
    private class CommentDTO {
        Comment data;
        List<Comment> replies;

        CommentDTO(Comment comment) {
            this.data = comment;
            this.replies = comment.getChildComments();
        }
    }

    @Getter
    private class CategoryDTO {
        Integer id;
        String name;
        Integer level;

        CategoryDTO(Category category) {
            this.id = category.getCategoryId();
            this.name = category.getName();
            this.level = category.getLevel();
        }
    }

    private String name;
    private List<ProductLinkDTO> assets;            // Including all product images
    private List<CommentDTO> comments;
    private List<CategoryDTO> categories;           // The product category itself and all of its accestor categories.
    private String introduction;
    private String description;
    private String seller;
    private String sellerName;
    private String avatar;
    private Integer price;
    private Integer discount;
    private Boolean isOnSale;
    private ProductStatus status;
    private Integer numberOfStar;
    private Integer numberOfRating;
    

    private void setComments(List<Comment> comments) {
        // Filter out the comments that have no parent. Then select those comment and their replies to send to front-end.
        this.comments = comments.stream().filter(comment -> comment.getParentComment() == null).map(CommentDTO::new).toList();
    }

    private void setAssets(List<ProductLink> assets) {
        this.assets = assets.stream().map(ProductLinkDTO::new).toList();
    }

    private void setCategories(List<Category> categories) {
        this.categories = categories.stream().map(CategoryDTO::new).toList();
    }

    public ProductDetailDTO(Product product, List<Category> categories) {

        this.name = product.getName();
        this.introduction = product.getIntroduction();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.discount = product.getDiscount();
        this.isOnSale = product.getIsOnSale();
        this.status = product.getStatus();
        this.numberOfStar = product.getNumberOfStar();

        User seller = product.getSeller();
        this.seller = seller.getUsername();
        this.sellerName = seller.getName();
        this.avatar = seller.getAvatar();

        setComments(product.getComments());
        setAssets(product.getAssets());
        setCategories(categories);
        
    }

}
