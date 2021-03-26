package com.codegym.service;

import com.codegym.model.Customer;
import com.codegym.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {
    Iterable<Product> findAll();

    Page<Product> findAll(Pageable pageable);
    Optional<Product> findById(Integer id);

    void save(Product customer);

    void remove(Integer id);
}
