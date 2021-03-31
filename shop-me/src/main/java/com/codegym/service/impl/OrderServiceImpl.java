package com.codegym.service.impl;

import com.codegym.model.Order;
import com.codegym.model.Province;
import com.codegym.repository.OrderRepository;
import com.codegym.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Iterable<Order> findAllByIsDeleteIsFalse() {
        return orderRepository.findAllByIsDeleteIsFalse();
    }

    @Override
    public Optional<Order> findById(Integer id) {
        return orderRepository.findById(id);
    }

    @Override
    public void saveOrUpdate(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void remove(Integer id) {
        orderRepository.deleteById(id);
    }
}
