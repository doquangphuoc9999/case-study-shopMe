package com.codegym.service.impl;

import com.codegym.model.PostBlog;
import com.codegym.repository.PostBlogRepository;
import com.codegym.service.PostBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostBlogServiceImpl implements PostBlogService {
    @Autowired
    private PostBlogRepository postBlogRepository;


    @Override
    public Page<PostBlog> findAllByDeletedFalse(Pageable pageable) {
        return postBlogRepository.findAllByDeletedFalse(pageable);
    }

    @Override
    public Page<PostBlog> findAll(Pageable pageable) {
        return postBlogRepository.findAll(pageable);
    }

    @Override
    public Optional<PostBlog> findById(Integer id) {
        return postBlogRepository.findById(id);
    }

    @Override
    public void save(PostBlog postBlog) {
        postBlogRepository.save(postBlog);
    }

    @Override
    public void remove(Integer id) {
        postBlogRepository.deleteById(id);
    }
}
