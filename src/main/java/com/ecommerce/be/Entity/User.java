package com.ecommerce.be.Entity;

import java.io.Serializable;
import java.time.ZonedDateTime;

import com.ecommerce.be.Constant.UserRole;
import com.ecommerce.be.Dto.RegisterRequestDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @Column(length = 50, nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(length = 10, nullable = false)
    private String name;
    @Column(length = 50, nullable = false)
    private String email;
    @Column
    private String address;
    @Column(length = 30)
    private String numPhone;
    @Column
    private Character gender;
    @Column
    private String loginType;
    @Column(insertable = false, updatable = false)
    private ZonedDateTime createdAt;
    @Column
    private String avatar;
    @Column(nullable = false)
    private String role = UserRole.BUYER;
    @Column()
    private ZonedDateTime lockUntil;
    @Column(length = 50)
    private String nation;
    @Column(length = 50)
    private String shopName;

    public User(RegisterRequestDTO registerRequest) {
        this.username = registerRequest.username();
        this.password = registerRequest.password();
        this.name = registerRequest.username();
        this.email = registerRequest.email();
    }
}