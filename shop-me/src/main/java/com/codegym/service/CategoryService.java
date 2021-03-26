package com.codegym.service;

import com.codegym.model.CategoryBlog;
import com.codegym.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CategoryService {
    Page<CategoryBlog> selectAll(Pageable pageable);
    Page<CategoryBlog> findAllByDeletedFalse(Pageable pageable);

    Optional<CategoryBlog> findById(Integer id);

    void save(CategoryBlog customer);

    void remove(Integer id);
}
