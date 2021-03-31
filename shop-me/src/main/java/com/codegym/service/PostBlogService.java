package com.codegym.service;

import com.codegym.model.PostBlog;
import com.codegym.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PostBlogService {
    Page<PostBlog> findAllByDeletedFalse(Pageable pageable);
    Page<PostBlog> findAll(Pageable pageable);
    Optional<PostBlog> findById(Integer id);

    void save(PostBlog postBlog);

    void remove(Integer id);

    Page<PostBlog> findAllByCategoryBlogIdOrderByPublishDateDesc(Integer id, Pageable pageable);
}
