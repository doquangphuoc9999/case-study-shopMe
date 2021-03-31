package com.codegym.controller;

import com.codegym.model.Order;
import com.codegym.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.awt.event.MouseListener;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @GetMapping("/listOrder")
    public ModelAndView listOrder( ){
        Iterable<Order> listOrder = orderService.findAllByIsDeleteIsFalse();
        ModelAndView mav=  new ModelAndView("order/list");
        mav.addObject("listOrder",listOrder);
        return mav;
    }

    @GetMapping("/createOrder")
    public ModelAndView createOrder(){
        ModelAndView mav  = new ModelAndView("order/create");
        mav.addObject("order", new Order());
        return mav;
    }
}
