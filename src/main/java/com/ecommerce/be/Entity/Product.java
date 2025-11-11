package com.ecommerce.be.Entity;

import java.time.ZonedDateTime;
import java.util.List;


import com.ecommerce.be.Constant.ProductStatus;
import com.ecommerce.be.Converter.ProductStatusConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Setter
@Builder()
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @Column()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @Column()
    private String name;

    @Column()
    private Integer price;

    @Column()
    private String description;

    @Column()
    private Integer category;

    @Column()
    private String material;

    @Column()
    private Integer quantity;

    @Column()
    private Boolean isOnSale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller")
    private User seller;

    @Column(insertable = false, updatable = false)
    private ZonedDateTime createdAt;

    @Column()
    private ZonedDateTime approvedAt;

    @Column(nullable = true)
    private String approver;

    @Column()
    @Convert(converter = ProductStatusConverter.class)
    @Builder.Default
    private ProductStatus status = ProductStatus.ACTIVE;

    @Column()
    private String coverImage;

    @Column()
    private Integer discount;

    @Column()
    private Integer numberOfStar;

    @Column()
    private Integer numberOfRating;

    @Column()
    private Boolean approvedByAI;

    @Column()
    private String introduction;

    @Column()
    private String type;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductLink> assets;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Comment> comments;
}