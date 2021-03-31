package com.codegym.repository;

import com.codegym.model.PostBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostBlogRepository extends JpaRepository<PostBlog,Integer> {
    Page<PostBlog> findAllByDeletedFalse(Pageable pageable);

    Page<PostBlog> findAllByCategoryBlogIdOrderByPublishDateDesc(Integer id, Pageable pageable);
}
