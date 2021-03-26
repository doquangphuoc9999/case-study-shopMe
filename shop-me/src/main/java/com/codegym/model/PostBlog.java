package com.codegym.model;

import lombok.Data;
import org.hibernate.annotations.Where;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Where(clause = "deleted=false")
public class PostBlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String shortContent;
    private String fullContent;
    private LocalDateTime localDateTime = LocalDateTime.now();

    @Transient
    private CommonsMultipartFile[] image;

    private String imageUrl;

    private boolean deleted = false;

    @ManyToOne
    private CategoryBlog categoryBlog;

    @ManyToOne
    private User user;
}
