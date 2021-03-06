package com.codegym.controller;

import com.codegym.model.CategoryBlog;
import com.codegym.model.PostBlog;
import com.codegym.model.User;
import com.codegym.service.impl.CategoryServiceImpl;
import com.codegym.service.impl.PostBlogServiceImpl;
import com.codegym.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@Controller
@RequestMapping("/postBlog")
public class PostBlogController {
    @Autowired
    private PostBlogServiceImpl postBlogService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    CategoryServiceImpl categoryService;

    @ModelAttribute("listUser")
    public Iterable<User> listUsers(){
        return userService.selectAll();
    }

    @ModelAttribute("listCategory")
    public Iterable<CategoryBlog> listCategory(Pageable pageable){
       return categoryService.findAllByDeletedFalse(pageable);
    }

    @GetMapping("/listPost")
    public ModelAndView listPost(@PageableDefault(size = 7, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable){
        Page<PostBlog> listPost = postBlogService.findAllByDeletedFalse(pageable);
        ModelAndView mav = new ModelAndView("postBlog/listPost");
        mav.addObject("listPost",listPost);
        return mav;
    }

    @GetMapping("/createPost")
    public String createPost(Model model){
        model.addAttribute("post",new PostBlog());
        return "postBlog/addPost";
    }

    @PostMapping("/addPost")
    public String addPost(@Valid@ModelAttribute("post") PostBlog postBlog, BindingResult bindingResult, HttpServletRequest request, RedirectAttributes model) throws Exception {
        new PostBlog().validate(postBlog, bindingResult);
        if (bindingResult.hasFieldErrors()){
            return "postBlog/addPost";
        }
        String uploadRootPath = request.getServletContext().getRealPath("upload");
        File uploadRootDir = new File(uploadRootPath);

        String uploadLocalPath = "E:\\Module-4\\case-module4\\shop-me\\src\\main\\webapp\\upload";
        File uploadLocalDir = new File(uploadLocalPath);

        // T???o th?? m???c g???c upload n???u n?? kh??ng t???n t???i.
        if (!uploadRootDir.exists()){
            uploadRootDir.mkdir();
        }
        CommonsMultipartFile[] files = postBlog.getImage();
//        Map<File, String> uploadFile = new HashMap<>();
        for (CommonsMultipartFile commonsMultipartFile : files){
            // T??n file g???c t???i Clien
            String name = commonsMultipartFile.getOriginalFilename();
            if (name != null && name.length() > 0){
                // T???o file t???i Server
                File severFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);

                // Lu???ng ghi d??? li???u v??o file tr??n Server
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(severFile));
                stream.write(commonsMultipartFile.getBytes());
                stream.close();

                File localFile = new File(uploadLocalDir.getAbsolutePath() + File.separator + name);

                // Lu???ng ghi d??? li???u v??o file tr??n Server
                BufferedOutputStream streamLocal = new BufferedOutputStream(new FileOutputStream(localFile));
                streamLocal.write(commonsMultipartFile.getBytes());
                streamLocal.close();

                postBlog.setImageUrl(name);
            }
        }
        postBlogService.save(postBlog);
        model.addFlashAttribute("mess","add is success");
        return "redirect:/postBlog/listPost";
    }

    @GetMapping("/{id}/view")
    public String viewPost(@PathVariable("id") Integer id, Model model){
        PostBlog postBlog = postBlogService.findById(id).get();
        if (postBlog != null){
            model.addAttribute("postBlog",postBlog);
            return "postBlog/viewPost";
        }
        return "error";
    }

    @GetMapping("/{id}/edit")
    public String editPost(@PathVariable("id") Integer id, Model model){
        PostBlog postBlog = postBlogService.findById(id).get();
        if (postBlog != null){
            model.addAttribute("postBlog",postBlog);
            return "postBlog/editPost";
        }
        return "error";
    }

    @PostMapping("/editPost")
    public String editPost(@ModelAttribute("postBlog") PostBlog postBlog,RedirectAttributes redirectAttributes,HttpServletRequest request, Model model) throws IOException {
        String uploadRootPath = request.getServletContext().getRealPath("upload");
        File uploadRootDir = new File(uploadRootPath);

        String uploadLocalPath = "E:\\Module-4\\case-module4\\shop-me\\src\\main\\webapp\\upload";
        File uploadLocalDir = new File(uploadLocalPath);

        // T???o th?? m???c g???c upload n???u n?? kh??ng t???n t???i.
        if (!uploadRootDir.exists()){
            uploadRootDir.mkdir();
        }
        CommonsMultipartFile[] files = postBlog.getImage();
//        Map<File, String> uploadFile = new HashMap<>();
        for (CommonsMultipartFile commonsMultipartFile : files){
            // T??n file g???c t???i Clien
            String name = commonsMultipartFile.getOriginalFilename();
            if (name != null && name.length() > 0){
                // T???o file t???i Server
                File severFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);

                // Lu???ng ghi d??? li???u v??o file tr??n Server
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(severFile));
                stream.write(commonsMultipartFile.getBytes());
                stream.close();

                File localFile = new File(uploadLocalDir.getAbsolutePath() + File.separator + name);

                // Lu???ng ghi d??? li???u v??o file tr??n Server
                BufferedOutputStream streamLocal = new BufferedOutputStream(new FileOutputStream(localFile));
                streamLocal.write(commonsMultipartFile.getBytes());
                streamLocal.close();

                postBlog.setImageUrl(name);
            }
        }
        postBlogService.save(postBlog);
        redirectAttributes.addFlashAttribute("mess","Edit is success");
        return "redirect:/postBlog/listPost";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes){
        PostBlog postBlog = postBlogService.findById(id).get();
        if (postBlog != null){
            postBlog.setDeleted(true);
            postBlogService.save(postBlog);
            redirectAttributes.addFlashAttribute("mess","Delete success");
            return "redirect:/postBlog/listPost";
        }
        return "error";
    }
}
