package com.codegym.model;

import lombok.Data;
import org.hibernate.annotations.Where;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Where(clause = "deleted=false")
@Transactional
public class PostBlog implements Validator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String shortContent;

    private String fullContent;

    private LocalDateTime publishDate = LocalDateTime.now();

    @Transient
    private CommonsMultipartFile[] image;

    private String imageUrl;

    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    private CategoryBlog categoryBlog;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Override
    public boolean supports(Class<?> aClass) {
        return PostBlog.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        PostBlog postBlog = (PostBlog) o;
        String title = postBlog.getTitle();
        String shortContent = postBlog.getShortContent();
        String fullContent = postBlog.getFullContent();

        ValidationUtils.rejectIfEmpty(errors,"title","title.empty");
        ValidationUtils.rejectIfEmpty(errors,"shortContent","shortContent.empty");
        ValidationUtils.rejectIfEmpty(errors,"fullContent","fullContent.empty");


        if (title.length() > 150 || title.length() <5){
            errors.rejectValue("title","title.length");
        }
    }
}
