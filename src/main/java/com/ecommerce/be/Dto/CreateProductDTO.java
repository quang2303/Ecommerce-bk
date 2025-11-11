package com.ecommerce.be.Dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public record CreateProductDTO(
    String name,
    Integer price,
    Integer quantity,
    String material,
    Integer category,
    String type,
    String description,
    String introduction,
    List<MultipartFile> images,
    String option1,
    List<String> chooseOptions1,
    String option2,
    List<String> chooseOptions2,
    List<MultipartFile> videos
) {
}