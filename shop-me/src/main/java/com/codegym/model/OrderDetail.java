package com.codegym.model;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
@Getter
@Setter
@Entity
@Table(name = "oderDetails")
public class OrderDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    private Product product;
    private Integer quantity;
    private double priceOder;

    @Where(clause = "delete=false")
    private boolean isDelete = false;

    private String status;
    @ManyToOne
    @JoinColumn(name = "idOrder")
    private Order order;


    public OrderDetail() {
    }
    public OrderDetail(Integer id, Product product, Integer quantity, double priceOder, String status) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.priceOder = priceOder;
        this.status = status;
    }
}