package com.codegym.model;

import lombok.Data;
import org.hibernate.annotations.Where;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Where(clause = "deleted=false")
public class CategoryBlog implements Validator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nameCategory;

    private boolean deleted = false;

    @OneToMany(mappedBy = "categoryBlog")
    List<PostBlog> list = new ArrayList<>();


    @Override
    public boolean supports(Class<?> aClass) {
        return CategoryBlog.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CategoryBlog categoryBlog = (CategoryBlog) o;
        String nameCategory = categoryBlog.getNameCategory();

        ValidationUtils.rejectIfEmpty(errors,"nameCategory","nameCategory.empty");

        if (nameCategory.length() > 30 || nameCategory.length() < 3){
            errors.rejectValue("nameCategory","nameCategory.length");
        }

    }
}
