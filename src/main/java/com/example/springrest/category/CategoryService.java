package com.example.springrest.category;


import jakarta.validation.Valid;
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
        return categoryRepository.findAll(pageable);
    }

    public Category getCategoryById(long id){
        return categoryRepository.findById(id).orElseThrow();
    }

    public Category getCategoryByName(String name){
        return categoryRepository.findByName(name).orElseThrow();
    }

    public void addNewCategory(@Validated Category category){
        boolean categoryExist = categoryRepository.existsByName(category.getName());
        if(categoryExist){
            throw new RuntimeException("Category exists");
        }

        categoryRepository.save(category);
    }


}

