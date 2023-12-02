package com.example.springrest.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Page<Category> getAllCategories(Pageable pageable){
        System.out.println("TEST");
        return categoryRepository.findAll(pageable);
    }

    public Category getCategoryById(long id){
        return categoryRepository.findById(id).orElseThrow();
    }

    public Category getCategoryByName(String name){
        return categoryRepository.findByName(name).orElseThrow();
    }

    public void addNewCategory(@Validated Category category){
        if(categoryRepository.findByName(category.getName()).toString().equals(category.getName())){
            throw new RuntimeException("Category already exists");
        }
        categoryRepository.save(category);
    }

}

