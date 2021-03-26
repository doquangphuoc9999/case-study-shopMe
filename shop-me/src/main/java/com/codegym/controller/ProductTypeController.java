package com.codegym.controller;


import com.codegym.model.ProductType;
import com.codegym.service.impl.ProductTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Iterator;
import java.util.Optional;

@Controller
@RequestMapping("/productType")
public class ProductTypeController {

    @Autowired
    private ProductTypeServiceImpl productTypeService;

    @GetMapping("/list")
    public ModelAndView listProductType() {
        Iterable<ProductType> listProductType = productTypeService.findAll();
        ModelAndView mav = new ModelAndView("productType/listProductType");
        mav.addObject("listProductType", listProductType);
        return mav;
    }

    @GetMapping("/createProductType")
    public String addProductType(Model model) {
        model.addAttribute("productType", new ProductType());
        return "productType/addProductType";
    }

    @PostMapping("/addProductType")
    public String PostAddProductType(@ModelAttribute("productType") ProductType productType, Model model) {
        productTypeService.save(productType);
        model.addAttribute("mess", "Add is success");
        return "productType/addProductType";
    }

    @GetMapping("/{id}/view")
    public String viewProductType(@PathVariable("id") Integer id, Model model) {
        Optional<ProductType> productType = productTypeService.findById(id);
        if (productType != null) {
            model.addAttribute("productTypeView", productType);
            return "productType/viewProductType";
        } else {
            return "error";
        }
    }
}
