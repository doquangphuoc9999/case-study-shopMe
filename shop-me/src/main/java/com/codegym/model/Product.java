package com.codegym.model;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "products")
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE products SET deleted=true WHERE id=?")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String productName;

    @Transient
    private CommonsMultipartFile[] image;

    private String imageUrl;

    private double price;


    private boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "idType", referencedColumnName = "id")
    private ProductType productType;

    public Product() {
    }

    public Product(Integer id, String productName, String imageUrl, double price, ProductType productType) {
        this.id = id;
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.price = price;
        this.productType = productType;
    }
}