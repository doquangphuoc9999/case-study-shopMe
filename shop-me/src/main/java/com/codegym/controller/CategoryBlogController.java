package com.codegym.controller;


import com.codegym.model.CategoryBlog;
import com.codegym.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("categoryBlog")
public class CategoryBlogController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping("/listCategory")
    public ModelAndView listCategory(@PageableDefault(size = 8) Pageable pageable){
        Page<CategoryBlog> categoryBlogs  = categoryService.findAllByDeletedFalse(pageable);
        ModelAndView mav = new ModelAndView("categoryBlog/listCategory");
        mav.addObject("listCategory",categoryBlogs);
        return mav;
    }

    @GetMapping("/createCategoryBlog")
    public String createCategory(Model model){
        model.addAttribute("category", new CategoryBlog());
        return "categoryBlog/createCategory";
    }

    @PostMapping("/addCategory")
    public String addCategory(@ModelAttribute("category") CategoryBlog categoryBlog, Model model){
        categoryService.save(categoryBlog);
        model.addAttribute("mess","Add is success");
        return "categoryBlog/createCategory";
    }

    @GetMapping("/{id}/view")
    public String viewCategory(@PathVariable("id") Integer id, Model model){
        Optional<CategoryBlog> categoryBlog = categoryService.findById(id);
        if (categoryBlog != null){
            model.addAttribute("category", categoryBlog.get());
            return "categoryBlog/viewCategory";
        }
        return "error";
    }

    @GetMapping("/{id}/edit")
    public String editCategory(@PathVariable("id") Integer id, Model model){
        Optional<CategoryBlog> categoryBlog = categoryService.findById(id);
        if (categoryBlog != null){
            model.addAttribute("category", categoryBlog.get());
            return "categoryBlog/editCategory";
        }
        return "error";
    }

    @PostMapping("/editCategory")
    public String edit(@ModelAttribute("category") CategoryBlog categoryBlog, Model model){
        categoryService.save(categoryBlog);
        model.addAttribute("mess","update is success");
        return "categoryBlog/editCategory";
    }

    @GetMapping("/{id}/delete")
    public String deleteCategory(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes){
        CategoryBlog categoryBlog = categoryService.findById(id).get();
        if (categoryBlog != null){
            categoryBlog.setDeleted(true);
            categoryService.save(categoryBlog);
            redirectAttributes.addFlashAttribute("mess","delete is success");
            return "redirect:/categoryBlog/listCategory";
        }
        return "error";
    }
}
