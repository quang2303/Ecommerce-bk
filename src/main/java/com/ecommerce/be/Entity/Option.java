package com.ecommerce.be.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
import lombok.Getter;
import lombok.NoArgsConstructor;

// TODO: Change to ProductVariations
@Entity
@Table(name = "options")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Option {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer optionId;

    @Column
    private String optionName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentOptionId")
    @JsonIgnore
    private Option parentOption;

    @OneToMany(mappedBy = "parentOption", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Option> childOptions;
}
