package com.ecommerce.be.Repository;

import java.util.List;

import com.ecommerce.be.Dto.ProductFilterForAdminPageDTO;
import com.ecommerce.be.Dto.ProductFilterForSellerPageDTO;
import com.ecommerce.be.Entity.Product;

public interface ProductRepositoryCustom {
    List<Product> findByFilterInAdminPage(ProductFilterForAdminPageDTO filter);
    List<Product> findByFilterInSellerPage(ProductFilterForSellerPageDTO filter, String username);
}
