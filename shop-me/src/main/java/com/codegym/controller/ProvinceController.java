package com.codegym.controller;

import com.codegym.model.Province;
import com.codegym.service.impl.ProvinceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/province")
public class ProvinceController {

    @Autowired
    private ProvinceServiceImpl provinceService;


    @GetMapping("/listProvince")
    public ModelAndView listProvince() {
        Iterable<Province> provinceList = provinceService.findAllByIsDeleteIsFalse();
        ModelAndView mav = new ModelAndView("province/listProvince");
        mav.addObject("provinceList", provinceList);
        return mav;
    }

    @GetMapping("/createProvince")
    public ModelAndView formCreateProvince() {
        ModelAndView mav = new ModelAndView("province/create");
        mav.addObject("province", new Province());
        return mav;
    }

    @PostMapping("/saveProvince")
    public String saveProvince(@Valid @ModelAttribute(value = "province") Province province, BindingResult bindingResult, RedirectAttributes model) {
        new Province().validate(province, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            return "province/create";
        }
        provinceService.saveOrUpdate(province);
        model.addFlashAttribute("mess", "Create Province successfully");
        return "redirect:/province/listProvince";
    }

    @GetMapping("/{id}/edit")
    public ModelAndView formEditProvince(@PathVariable(value = "id") Integer id) {
        Optional<Province> province = provinceService.findById(id);
        ModelAndView mav;
        if (province == null) {
            mav = new ModelAndView("error");
        } else {
            mav = new ModelAndView("province/edit");
            mav.addObject("province", province);
        }
        return mav;
    }

    @PostMapping("/editProvince")
    public String editProvince(Province province, RedirectAttributes redirectAttributes) {
        provinceService.saveOrUpdate(province);
        redirectAttributes.addFlashAttribute("mess", "Edit Province successfully");
        return "redirect:/province/listProvince";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes){
        Province province = provinceService.findById(id).get();
        if (province != null){
            province.setDelete(true);
            provinceService.saveOrUpdate(province);
            redirectAttributes.addFlashAttribute("mess", "delete is success");
            return "redirect:/province/listProvince";
        }
        else {
            return "error";
        }
    }


}
