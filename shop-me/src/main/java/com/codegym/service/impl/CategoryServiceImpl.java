package com.codegym.service.impl;


import com.codegym.model.CategoryBlog;
import com.codegym.repository.CategoryRepository;
import com.codegym.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<CategoryBlog> selectAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Page<CategoryBlog> findAllByDeletedFalse(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Optional<CategoryBlog> findById(Integer id) {
        return categoryRepository.findById(id);
    }

    @Override
    public void save(CategoryBlog categoryBlog) {
        categoryRepository.save(categoryBlog);
    }

    @Override
    public void remove(Integer id) {
        categoryRepository.deleteById(id);
    }
}
