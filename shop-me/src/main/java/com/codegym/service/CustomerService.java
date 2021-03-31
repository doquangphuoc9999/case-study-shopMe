package com.codegym.service;

import com.codegym.model.Customer;
import com.codegym.model.Province;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CustomerService {

    Page<Customer> selectAll(Pageable pageable);

    Optional<Customer> findById(Integer id);

    void save(Customer customer);

    void remove(Integer id);
}
