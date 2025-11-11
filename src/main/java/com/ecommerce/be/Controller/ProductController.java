package com.ecommerce.be.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.be.Dto.CreateProductDTO;
import com.ecommerce.be.Dto.ProductDetailDTO;
import com.ecommerce.be.Dto.ProductFilterForAdminPageDTO;
import com.ecommerce.be.Dto.ProductFilterForSellerPageDTO;
import com.ecommerce.be.Dto.ProductForAdminPageDTO;
import com.ecommerce.be.Dto.ProductForAdminPageMapper;
import com.ecommerce.be.Dto.ProductForHomePageDTO;
import com.ecommerce.be.Dto.ProductForHomePageMapper;
import com.ecommerce.be.Dto.ProductForSellerPageDTO;
import com.ecommerce.be.Dto.ProductForSellerPageMapper;
import com.ecommerce.be.Entity.Category;
import com.ecommerce.be.Entity.Product;
import com.ecommerce.be.ExceptionHandler.ErrorResponse;
import com.ecommerce.be.Service.CategoryService;
import com.ecommerce.be.Service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private ProductService productService;
    private CategoryService categoryService;
    private ProductForHomePageMapper productForHomePageMapper;
    private ProductForAdminPageMapper productForAdminPageMapper;
    private ProductForSellerPageMapper productForSellerPageMapper;

    public ProductController(ProductService productService, CategoryService categoryService,
            ProductForHomePageMapper productForHomePageMapper, ProductForAdminPageMapper productForAdminPageMapper,
            ProductForSellerPageMapper productForSellerPageMapper) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.productForHomePageMapper = productForHomePageMapper;
        this.productForAdminPageMapper = productForAdminPageMapper;
        this.productForSellerPageMapper = productForSellerPageMapper;
    }




    @Operation(summary = "Get products for home page", tags = { "Product" }, responses = {
        @ApiResponse(responseCode = "200", description = "Return a list of products, if system does not find any products it will return an empty list", 
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ProductForHomePageDTO.class, type = "array"))
        }),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content())
    })

    @GetMapping()
    public ResponseEntity<List<ProductForHomePageDTO>> getProductsForHomePage(
            @RequestParam(defaultValue = "") String searchTerm, @RequestParam(defaultValue = "") Integer category,
            @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer offset) {
        List<Product> prods = productService.getProductsForHomePage(searchTerm, category, page, offset);
        List<ProductForHomePageDTO> prodsDTO = prods.stream().map(productForHomePageMapper).collect(Collectors.toList());
        System.out.println("here");
        System.out.println(prodsDTO);
        return ResponseEntity.ok(prodsDTO);
    }




    @Operation(summary = "Get products for seller page", tags = { "Product" }, responses = {
        @ApiResponse(responseCode = "200", description = "Return a list of products, if system does not find any products it will return an empty list", 
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ProductForSellerPageDTO.class, type = "array"))
        }),
        @ApiResponse(responseCode = "401", description = "Unauthorized request", content = @Content()),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content())
    })

    @GetMapping("seller")
    @Secured("seller")
    public ResponseEntity<List<ProductForSellerPageDTO>> getProductsForSellerPage(ProductFilterForSellerPageDTO filter, Principal principal) {
        List<Product> prods = productService.getProductsForSellerPage(filter, principal.getName());
        List<ProductForSellerPageDTO> prodsDTO = prods.stream().map(productForSellerPageMapper).collect(Collectors.toList());
        return ResponseEntity.ok(prodsDTO);
    }





    @Operation(summary = "Get products for admin page", tags = { "Product" }, responses = {
        @ApiResponse(responseCode = "200", description = "Return a list of products, if system does not find any products it will return an empty list", 
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ProductForAdminPageDTO.class, type = "array"))
        }),
        @ApiResponse(responseCode = "401", description = "Unauthorized request", content = @Content()),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content())
    })

    @GetMapping("admin")
    @Secured("admin")
    private ResponseEntity<List<ProductForAdminPageDTO>> getProductsForAdminPage(ProductFilterForAdminPageDTO filter) {
        List<Product> prods = productService.getProductsForAdminPage(filter);
        List<ProductForAdminPageDTO> prodsDTO = prods.stream().map(productForAdminPageMapper)
                .collect(Collectors.toList());
        return ResponseEntity.ok(prodsDTO);
    }




    @Operation(summary = "Get product details", tags = { "Product" }, responses = {
        @ApiResponse(responseCode = "200", description = "Return product details", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDetailDTO.class))
        }),
        @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(
            mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)
        )),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content())
    })

    @GetMapping("{id}")
    public ResponseEntity<ProductDetailDTO> getProductDetails(@PathVariable Integer id) {
        Product prod = productService.getProduct(id);
        List<Category> categories = categoryService.getCategoryHierachies(prod.getCategory());
        ProductDetailDTO prodDTO = new ProductDetailDTO(prod, categories);
        return ResponseEntity.ok(prodDTO);
    }



    
    @Operation(summary = "Create product", tags = { "Product" }, responses = {
        @ApiResponse(responseCode = "200", description = "Return product details"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content())
    })

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @Secured("seller")
    public ResponseEntity<Void> createProduct(@ModelAttribute CreateProductDTO productDTO, Principal principal) {
        Product product = productService.createProduct(productDTO, principal.getName());
        return ResponseEntity.created(URI.create("/products/" + product.getProductId())).build();
    }
}
