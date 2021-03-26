package com.codegym.model;

import lombok.Data;
import org.hibernate.annotations.Where;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Where(clause = "deleted=false")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nameUser;
    private String gmailUser;
    private String nickname;
    private String introduce;
    private String passWordUser;

    @Transient
    private CommonsMultipartFile[] image;

    private String imageUrl;

    private boolean deleted = false;

    @OneToMany(mappedBy = "user")
    List<PostBlog> list = new ArrayList<>();


}
