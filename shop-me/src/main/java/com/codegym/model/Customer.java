package com.codegym.model;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Data
@Entity
@Where(clause = "isDelete=false")
@Table(name = "customers")
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String customerName;

    private String email;

    private String address;

    private String phoneNumber;

    private boolean isDelete = false;

    @ManyToOne
    @JoinColumn(name = "idProvince", referencedColumnName = "id")
    private Province province;
    @OneToMany(mappedBy = "customer")
    private List<Order> orders;



}