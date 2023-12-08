package com.example.springrest.category;

import com.example.springrest.Exception.ResourceAlreadyExistException;
import com.example.springrest.Exception.ResourceNotFoundException;
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

    public Category getCategoryById(Long id){
        return categoryRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(id.toString()));
    }

    public Category getCategoryByName(String name){
        return categoryRepository.findByName(name).orElseThrow(() ->
                new ResourceNotFoundException(name));
    }

    public void addNewCategory(@Validated Category category){
        if(categoryRepository.findByName(category.getName()).toString().equals(category.getName())){
            throw new ResourceAlreadyExistException(category.getName());
        }
        categoryRepository.save(category);
    }

}

