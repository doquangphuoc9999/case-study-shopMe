package com.codegym.service;

import com.codegym.model.Customer;
import com.codegym.model.Province;

import java.util.Optional;

public interface CustomerService {

    Iterable<Customer> findAll();


    Optional<Customer> findById(Integer id);

    void save(Customer customer);

    void remove(Integer id);
}
