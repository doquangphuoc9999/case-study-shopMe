package com.codegym.controller;

import com.codegym.model.PostBlog;
import com.codegym.model.User;
import com.codegym.service.impl.PostBlogServiceImpl;
import com.codegym.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;


@Controller
@RequestMapping("/postBlog")
public class PostBlogController {
    @Autowired
    private PostBlogServiceImpl postBlogService;

    @Autowired
    private UserServiceImpl userService;

    @ModelAttribute("user")
    public Iterable<User> listUsers(){
        return userService.selectAll();
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
    public String addPost(@ModelAttribute("post") PostBlog postBlog, HttpServletRequest request,Model model) throws Exception {
        String uploadRootPath = request.getServletContext().getRealPath("upload");
        File uploadRootDir = new File(uploadRootPath);

        String uploadLocalPath = "E:\\Module-4\\New folder\\CaseStudyModule4\\CaseStudyM4\\shop-me\\src\\main\\webapp\\upload";
        File uploadLocalDir = new File(uploadLocalPath);

        // Tạo thư mục gốc upload nếu nó không tồn tại.
        if (!uploadRootDir.exists()){
            uploadRootDir.mkdir();
        }
        CommonsMultipartFile[] files = postBlog.getImage();
//        Map<File, String> uploadFile = new HashMap<>();
        for (CommonsMultipartFile commonsMultipartFile : files){
            // Tên file gốc tại Clien
            String name = commonsMultipartFile.getOriginalFilename();
            if (name != null && name.length() > 0){
                // Tạo file tại Server
                File severFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);

                // Luồng ghi dữ liệu vào file trên Server
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(severFile));
                stream.write(commonsMultipartFile.getBytes());
                stream.close();

                File localFile = new File(uploadLocalDir.getAbsolutePath() + File.separator + name);

                // Luồng ghi dữ liệu vào file trên Server
                BufferedOutputStream streamLocal = new BufferedOutputStream(new FileOutputStream(localFile));
                streamLocal.write(commonsMultipartFile.getBytes());
                streamLocal.close();

                postBlog.setImageUrl(name);
            }
        }
        postBlogService.save(postBlog);
        model.addAttribute("mess","add is success");
        return "postBlog/addPost";
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
}
