package com.codegym.service.impl;

import com.codegym.model.User;
import com.codegym.repository.UserRepository;
import com.codegym.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public Page<User> selectAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Iterable<User> selectAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> findAllByDeletedFalse(Pageable pageable) {
        return userRepository.findAllByDeletedFalse(pageable);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public void save(User customer) {
        userRepository.save(customer);
    }

    @Override
    public void remove(Integer id) {
        userRepository.deleteById(id);
    }
}
