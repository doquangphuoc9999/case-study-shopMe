package com.codegym.service;

import com.codegym.model.Province;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProvinceService {

//    Iterable<Province> findAll();

    Iterable<Province> findAllByIsDeleteIsFalse();

    Optional<Province> findById(Integer id);

    void saveOrUpdate(Province province);

    void remove(Integer id);
}
