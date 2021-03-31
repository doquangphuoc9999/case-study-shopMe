package com.codegym.repository;

import com.codegym.model.Order;
import com.codegym.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Iterable<Order> findAllByIsDeleteIsFalse();
}
