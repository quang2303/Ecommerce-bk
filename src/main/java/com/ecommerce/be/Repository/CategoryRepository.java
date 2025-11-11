package com.ecommerce.be.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.be.Entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByCategoryParent(Integer categoryParent);

    List<Category> findByLevel(Integer level);

    @Query(value = """
            WITH RECURSIVE CategoryHierarchy AS (
                SELECT categoryId, name, categoryParent, level, isSelected, image
                FROM category
                WHERE categoryId = :categoryId
                UNION
                SELECT c.categoryId, c.name, c.categoryParent, c.level, c.isSelected, c.image
                FROM category c
                JOIN CategoryHierarchy ch ON c.categoryId = ch.categoryParent
            )
            SELECT categoryId, name, categoryParent, level, isSelected, image
            FROM CategoryHierarchy
            ORDER BY level ASC
            """, nativeQuery = true)
    List<Category> getCategoryHierachies(@Param("categoryId") Integer categoryId);
}