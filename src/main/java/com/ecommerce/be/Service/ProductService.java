package com.ecommerce.be.Service;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.be.Repository.ProductRepository;
import com.ecommerce.be.Util.FileUploaderUtil;

import jakarta.persistence.EntityManager;

import com.ecommerce.be.Constant.ErrorMessage;
import com.ecommerce.be.Dto.CreateProductDTO;
import com.ecommerce.be.Dto.ProductFilterForAdminPageDTO;
import com.ecommerce.be.Dto.ProductFilterForSellerPageDTO;
import com.ecommerce.be.Entity.Product;
import com.ecommerce.be.Entity.ProductLink;
import com.ecommerce.be.Entity.User;
import com.ecommerce.be.ExceptionHandler.NotFoundException;

@Service
public class ProductService {
    
    EntityManager entityManager;
    ProductRepository productRepo;
    FileUploaderUtil uploaderUtil;

    public ProductService(ProductRepository productRepo, EntityManager entityManager, FileUploaderUtil uploaderUtil) {
        this.uploaderUtil = uploaderUtil;
        this.productRepo = productRepo;
        this.entityManager = entityManager;
    }

    public Product getProduct(Integer id) {
        Product product = productRepo.findById(id).orElse(null);
        if (product == null)
            throw new NotFoundException(ErrorMessage.NOT_FOUND_PRODUCT);
        return product;
    }

    public List<Product> getProductsForHomePage(String searchTerm, Integer category, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return productRepo.findByNameAndCategory(searchTerm, category, pageRequest);
    }

    public List<Product> getProductsForAdminPage(ProductFilterForAdminPageDTO filter) {
        return productRepo.findByFilterInAdminPage(filter);
    }

    public List<Product> getProductsForSellerPage(ProductFilterForSellerPageDTO filter, String username) {
        List<Product> prods = productRepo.findByFilterInSellerPage(filter, username);
        return prods;
    }

    public Product createProduct(CreateProductDTO productDTO, String username) {
        List<MultipartFile> files = new ArrayList<>(productDTO.images());
        files.addAll(productDTO.videos());

        Map<String, String> results = uploaderUtil.uploadFiles(files);

        // Use getReference to fetch from cache location instead fetch from database.
        // More in here: https://vladmihalcea.com/spring-data-jpa-findbyid/
        User seller = entityManager.getReference(User.class, username);

        Product product = Product.builder().name(productDTO.name())
                .price(productDTO.price())
                .description(productDTO.description())
                .quantity(productDTO.quantity())
                .material(productDTO.material())
                .category(productDTO.category())
                .type(productDTO.type())
                .introduction(productDTO.introduction())
                .seller(seller)
                .build();

        List<ProductLink> links = results.entrySet().stream()
                .map(e -> ProductLink.builder().link(e.getKey()).type(e.getValue()).product(product).build())
                .toList();

        product.setCoverImage(links.get(0).getLink());
        product.setAssets(links);
        
        // Return the saved product
        return productRepo.save(product);
    }
}
