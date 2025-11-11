package com.ecommerce.be.Dto;

public record CreateCommentDTO(
    Integer productId,
    Integer numberOfStars,
    String content,
    Integer parentId
) {
}
