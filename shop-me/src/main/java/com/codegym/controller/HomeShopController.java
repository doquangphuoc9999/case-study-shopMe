package com.codegym.controller;

import com.codegym.model.PostBlog;
import com.codegym.service.impl.PostBlogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("homePage")
public class HomeShopController {

    @Autowired
    private PostBlogServiceImpl postBlogService;

    @GetMapping("/homeShop")
    public ModelAndView homeShop(){
        ModelAndView mav = new ModelAndView("homePage/homeShop");
        return mav;
    }

    @GetMapping("/blogShop")
    public String blogShop(Model model, @RequestParam(name = "page", defaultValue = "1") int page){
        PageRequest pageRequest = PageRequest.of(page - 1,3,Sort.Direction.DESC,"publishDate");
        Page<PostBlog> blogPage = postBlogService.findAll(pageRequest);
        blogPage.getPageable().getPageNumber();
        model.addAttribute("blogPage",blogPage);
        return "homePage/blogShop";
    }

    @GetMapping("/{id}/blogSingle")
    public String blogSingle(@PathVariable("id") Integer id, Model model){
        PostBlog postBlog = postBlogService.findById(id).get();
        if (postBlog != null){
            model.addAttribute("blogSingle",postBlog);
            return "homePage/blogDetails";
        }
        return "error";
    }
}
