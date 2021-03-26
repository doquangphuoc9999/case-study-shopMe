package com.codegym.controller;

import com.codegym.model.Product;
import com.codegym.model.ProductType;
import com.codegym.service.impl.ProductServiceImpl;
import com.codegym.service.impl.ProductTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private ProductTypeServiceImpl productTypeService;

    @ModelAttribute("productType")
    public Iterable<ProductType> listProductType(){
        return productTypeService.findAll();
    }

    @GetMapping("/listProduct")
    public ModelAndView listProduct(@PageableDefault(size = 5) Pageable pageable){
        Iterable<Product> listProduct = productService.findAll(pageable);
        ModelAndView mav = new ModelAndView("product/listProduct");
        mav.addObject("listProduct",listProduct);
        return mav;
    }

    @GetMapping("/createProduct")
    public String createProduct(Model model){
        model.addAttribute("product", new Product());
        return "product/createProduct";
    }

//   thêm sp kèm upload file
    @PostMapping("/addPostProduct")
    public String addCreateProduct(HttpServletRequest request,Model model, @ModelAttribute("product")Product product) throws Exception {
        String uploadRootPath = request.getServletContext().getRealPath("upload");
        File uploadRootDir = new File(uploadRootPath);

        String uploadLocalPath = "E:\\Module-4\\New folder\\CaseStudyModule4\\CaseStudyM4\\shop-me\\src\\main\\webapp\\upload";
        File uploadLocalDir = new File(uploadLocalPath);

        // Tạo thư mục gốc upload nếu nó không tồn tại.
        if (!uploadRootDir.exists()){
            uploadRootDir.mkdir();
        }
        CommonsMultipartFile[] files = product.getImage();
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

                product.setImageUrl(name);
            }
        }
        productService.save(product);
        model.addAttribute("mess", "add is success");
        return "product/createProduct";
    }

    @GetMapping("/{id}/view")
    public String viewProduct(@PathVariable("id") Integer id,Model model){
        Optional<Product> product = productService.findById(id);
        if (product != null){
            model.addAttribute("product",product.get());
            return "product/viewProduct";
        }
        return "error";
    }

    @GetMapping("/{id}/edit")
    public String editProduct(@PathVariable("id") Integer id,Model model){
        Optional<Product> product = productService.findById(id);
        if (product != null){
           model.addAttribute("product",product.get());
           return "product/editProduct";
        }
        return "error";
    }

    @PostMapping("/editPostProduct")
    public String editPostProduct(HttpServletRequest request,@ModelAttribute("product") Product product, Model model) throws Exception {
        String uploadRootPath = request.getServletContext().getRealPath("upload");
        File uploadRootDir = new File(uploadRootPath);

        String uploadLocalPath = "E:\\Module-4\\New folder\\CaseStudyModule4\\CaseStudyM4\\shop-me\\src\\main\\webapp\\upload";
        File uploadLocalDir = new File(uploadLocalPath);

        if (!uploadLocalDir.exists()){
            uploadLocalDir.mkdir();
        }
        CommonsMultipartFile[] files = product.getImage();
        for (CommonsMultipartFile commonsMultipartFile : files){
//          lấy tên file gốc phía client
            String name = commonsMultipartFile.getOriginalFilename();
            if (name != null && name.length() > 0) {
                File severFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);
//          Luồng ghi của dữ liệu vào sever
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(severFile));
            stream.write(commonsMultipartFile.getBytes());
            stream.close();


//           file tại local
            File localFile = new File(uploadLocalDir.getAbsolutePath() + File.separator + name);
            BufferedOutputStream localStream = new BufferedOutputStream(new FileOutputStream(localFile));
            localStream.write(commonsMultipartFile.getBytes());
            localStream.close();

            product.setImageUrl(name);
            }
        }
        if (product != null){
            productService.save(product);
            model.addAttribute("mess","update is success");
            return "product/editProduct";
        }
        return "error";
    }

    @GetMapping("/{id}/delete")
    public String deleteProduct(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes){
        Product product = productService.findById(id).get();
        if (product != null){
            //product.setDeleted(true);
            productService.remove(id);
            redirectAttributes.addFlashAttribute("mess","Delete success");
            return "redirect:/product/listProduct";
        }
        return "error";
    }



}
