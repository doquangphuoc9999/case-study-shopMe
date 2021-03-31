package com.codegym.service;

import com.codegym.model.Order;
import com.codegym.model.Province;

import java.util.Optional;

public interface OrderService {
    Iterable<Order> findAllByIsDeleteIsFalse();

    Optional<Order> findById(Integer id);

    void saveOrUpdate(Order order);

    void remove(Integer id);
}
