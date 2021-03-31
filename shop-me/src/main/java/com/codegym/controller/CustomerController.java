package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.service.impl.CustomerServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerServiceImpl customerService;

    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/list")
    public ModelAndView listCustomer(Pageable pageable){
        Page<Customer> customerList = customerService.selectAll(pageable);
        ModelAndView mav = new ModelAndView("customer/list");
        mav.addObject("customerList", customerList);
        return mav;
    }
}
