package com.codegym.service;

import com.codegym.model.CategoryBlog;
import com.codegym.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Page<User> selectAll(Pageable pageable);

    Iterable<User> selectAll();
    Page<User> findAllByDeletedFalse(Pageable pageable);

    Optional<User> findById(Integer id);

    void save(User customer);

    void remove(Integer id);
}
