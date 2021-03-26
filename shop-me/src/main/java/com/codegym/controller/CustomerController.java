package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.service.impl.CustomerServiceImpl;
import com.codegym.service.impl.ProvinceServiceImpl;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerService;




    @GetMapping("/list")
    public ModelAndView listCustomer(){
        Iterable<Customer> customerList = customerService.findAll();
        ModelAndView mav = new ModelAndView("customer/list");
        mav.addObject("customerList", customerList);
        return mav;
    }
}
