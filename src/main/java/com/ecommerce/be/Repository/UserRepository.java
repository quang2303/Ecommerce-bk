package com.ecommerce.be.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.be.Entity.User;

public interface UserRepository extends JpaRepository<User, String> {
    
    @Modifying(clearAutomatically = true)       // Need to clear persistent context to sync with database
    @Query(value = """
        UPDATE user
        SET shopName = ?1, name = ?2, email = ?3, address = ?4, numPhone = ?5, nation = ?6, role = 'seller'
        WHERE username = ?7
    """, nativeQuery = true)
    public Integer updateSellerInfo(  
        String shopName, 
        String name,
        String email,
        String address,
        String numPhone,
        String nation,
        String username
    );

}
