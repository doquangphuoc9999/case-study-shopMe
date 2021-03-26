package com.codegym.service.impl;

import com.codegym.model.Province;
import com.codegym.repository.ProvinceRepository;
import com.codegym.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ProvinceServiceImpl implements ProvinceService {

    @Autowired
    private ProvinceRepository provinceRepository;

    @Override
    public Iterable<Province> findAllByIsDeleteIsFalse() {
        return provinceRepository.findAllByIsDeleteIsFalse();
    }

    @Override
    public Optional<Province> findById(Integer id) {
        return provinceRepository.findById(id);
    }

    @Override
    public void saveOrUpdate(Province province) {
        provinceRepository.save(province);
    }

    @Override
    public void remove(Integer id) {
        provinceRepository.deleteById(id);
    }
}
