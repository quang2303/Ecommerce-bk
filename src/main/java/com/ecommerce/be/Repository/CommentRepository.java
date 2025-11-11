package com.ecommerce.be.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.be.Entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
