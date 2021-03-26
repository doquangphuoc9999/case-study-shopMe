package com.codegym.service;

import com.codegym.model.ProductType;

import java.util.Optional;

public interface ProductTypeService{
    Iterable<ProductType> findAll();


    Optional<ProductType> findById(Integer id);

    void save(ProductType productType);

    void remove(Integer id);
}
