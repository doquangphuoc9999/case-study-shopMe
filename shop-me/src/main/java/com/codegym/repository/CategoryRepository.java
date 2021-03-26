package com.codegym.repository;

import com.codegym.model.CategoryBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryBlog,Integer> {
    Page<CategoryBlog> findAllByDeletedFalse(Pageable pageable);
}
