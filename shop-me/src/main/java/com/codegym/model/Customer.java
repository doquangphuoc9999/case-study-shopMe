package com.codegym.model;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String customerName;
    private String email;
    private String address;
    private String phoneNumber;

    @Where(clause = "delete=false")
    private boolean isDelete = false;

    @ManyToOne
    @JoinColumn(name = "idProvince", referencedColumnName = "id")
    private Province province;
    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
    public Customer() {
    }
    public Customer(Integer id, String customerName, String email, String address, String phoneNumber) {
        this.id = id;
        this.customerName = customerName;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
    public Customer(Integer id, String customerName, String email, String address, String phoneNumber, Province province) {
        this.id = id;
        this.customerName = customerName;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.province = province;
    }
}