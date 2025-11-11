package com.ecommerce.be.Controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.be.Dto.CreateCommentDTO;
import com.ecommerce.be.Entity.Category;
import com.ecommerce.be.Service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@RestController
@RequestMapping("api/v1/comments")
public class CommentController {
    
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }




    @Operation(summary = "Create comment", tags = { "Comment" }, responses = {
        @ApiResponse(responseCode = "201", description = "Create successfully", content = @Content()),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content())
    })

    @PostMapping()
    public ResponseEntity<Void> createComment(@RequestBody CreateCommentDTO createCommentDTO, Principal principal) {
        commentService.createComment(createCommentDTO, principal.getName());
        return ResponseEntity.created(null).build();
    }
}
