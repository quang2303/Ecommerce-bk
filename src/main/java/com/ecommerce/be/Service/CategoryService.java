package com.ecommerce.be.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.be.Repository.CategoryRepository;
import com.ecommerce.be.Entity.Category;

@Service
public class CategoryService {
    final private int GIFT_ID = 85;

    CategoryRepository categoryRepo;

    public CategoryService(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public List<Category> getCategoryChildren(Integer parentId) {
        return categoryRepo.findByCategoryParent(parentId);
    }

    public List<Category> getGifts() {
        return categoryRepo.findByCategoryParent(GIFT_ID);
    }

    public List<Category> getCategoryByLevel(Integer level) {
        return categoryRepo.findByLevel(level);
    }

    public List<Category> getCategoryHierachies(Integer categoryId) {
        return categoryRepo.getCategoryHierachies(categoryId);
    }
}
