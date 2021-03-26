package com.codegym.model;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Where(clause = "deleted=false")
public class CategoryBlog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nameCategory;

    private boolean deleted = false;

    @OneToMany(mappedBy = "categoryBlog")
    List<PostBlog> list = new ArrayList<>();


}
