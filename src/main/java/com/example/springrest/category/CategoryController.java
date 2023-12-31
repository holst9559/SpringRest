package com.example.springrest.category;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public Page<Category> getAllCategories(Pageable pageable){
        return categoryService.getAllCategories(pageable);
    }

    @GetMapping("/{id:\\d+}")
    public Category getCategoryById(@PathVariable Long id){
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/{name:.*\\D.*}")
    public Category getCategoryByName(@PathVariable String name){
        return categoryService.getCategoryByName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewCategory(@RequestBody @Validated Category category) {
        categoryService.addNewCategory(category);
    }





}
