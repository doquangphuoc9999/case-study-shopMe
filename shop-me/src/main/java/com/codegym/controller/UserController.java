package com.codegym.controller;

import com.codegym.model.User;
import com.codegym.service.impl.ProvinceServiceImpl;
import com.codegym.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ProvinceServiceImpl provinceService;

    @GetMapping("/listUser")
    public ModelAndView listUser(@PageableDefault(size = 8) Pageable pageable) {
        Page<User> listUser = userService.findAllByDeletedFalse(pageable);
        ModelAndView mav = new ModelAndView("user/listUser");
        mav.addObject("listUser", listUser);
        return mav;
    }

    @GetMapping("/createUser")
    public String createUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("lisProvince",provinceService.findAllByIsDeleteIsFalse());
        return "user/createUser";
    }

    @PostMapping("/addUser")
    public String addUser(@Valid @ModelAttribute("user") User user, RedirectAttributes model, HttpServletRequest request, BindingResult bindingResult) throws Exception {
        new User().validate(user,bindingResult);
        if (bindingResult.hasFieldErrors()){
            return "user/createUser";
        }
        String uploadRootPath = request.getServletContext().getRealPath("upload");
        File uploadRootDir = new File(uploadRootPath);

        String uploadLocalPath = "E:\\Module-4\\case-module4\\shop-me\\src\\main\\webapp\\upload";
        File uploadLocalDir = new File(uploadLocalPath);

        // Tạo thư mục gốc upload nếu nó không tồn tại.
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdir();
        }
        CommonsMultipartFile[] files = user.getImage();
//        Map<File, String> uploadFile = new HashMap<>();
        for (CommonsMultipartFile commonsMultipartFile : files) {
            // Tên file gốc tại Clien
            String name = commonsMultipartFile.getOriginalFilename();
            if (name != null && name.length() > 0) {
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

                user.setImageUrl(name);
            }
        }
        userService.save(user);
        model.addFlashAttribute("mess","add is success");
        return "redirect:/user/listUser";
    }

    @GetMapping("/{id}/view")
    public String viewUser(@PathVariable("id") Integer id, Model model){
        User user = userService.findById(id).get();
        if (user != null){
            model.addAttribute("user",user);
            return "user/viewUser";
        }
        return "error";
    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable("id") Integer id, Model model){
        User user = userService.findById(id).get();
        if (user != null){
            model.addAttribute("user",user);
            return "user/editUser";
        }
        return "error";
    }

    @PostMapping("/editUser")
    public String editUser(@ModelAttribute("user") User user,Model model, HttpServletRequest request) throws IOException {
        String uploadRootPath = request.getServletContext().getRealPath("upload");
        File uploadRootDir = new File(uploadRootPath);

        String uploadLocalPath = "E:\\Module-4\\case-module4\\shop-me\\src\\main\\webapp\\upload";
        File uploadLocalDir = new File(uploadLocalPath);

        // Tạo thư mục gốc upload nếu nó không tồn tại.
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdir();
        }
        CommonsMultipartFile[] files = user.getImage();
//        Map<File, String> uploadFile = new HashMap<>();
        for (CommonsMultipartFile commonsMultipartFile : files) {
            // Tên file gốc tại Clien
            String name = commonsMultipartFile.getOriginalFilename();
            if (name != null && name.length() > 0) {
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

                user.setImageUrl(name);
            }
        }
        userService.save(user);
        model.addAttribute("mess","Update is success");
        return "user/editUser";
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes){
        User user = userService.findById(id).get();
        if (user != null){
            user.setDeleted(true);
            userService.save(user);
            redirectAttributes.addFlashAttribute("mess","Delete is success");
            return "redirect:/user/listUser";
        }
        return "error";
    }
}
