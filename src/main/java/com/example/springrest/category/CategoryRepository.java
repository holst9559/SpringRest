package com.example.springrest.category;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @EntityGraph(attributePaths = {"places"})
    Optional<Category> findByName(String name);

    @Override
    @EntityGraph(attributePaths = {"places"})
    Optional<Category> findById(Long along);

}
