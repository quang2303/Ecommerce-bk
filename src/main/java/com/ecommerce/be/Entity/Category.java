package com.ecommerce.be.Entity;


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
@Table(name = "category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @Column()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;
    @Column()
    private String name;
    // TODO: Change to OneToOne relationship.
    @Column
    private Integer categoryParent;
    @Column
    private Integer level;
    @Column
    private String image;
    @Column
    private Boolean isSelected;
}
