package com.codegym.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "provinces")
public class Province implements Validator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String provinceName;

    @Where(clause = "isDelete=false")
    private boolean isDelete = false;

    @OneToMany(mappedBy = "province")
    private List<Customer> customers;


    @OneToMany(mappedBy = "province")
    private List<User> list = new ArrayList<>();

    @Override
    public boolean supports(Class<?> aClass) {
        return Province.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Province province = (Province) o;
        String name = province.getProvinceName();
        ValidationUtils.rejectIfEmpty(errors, "provinceName","nameProvince.empty");
        if (name.length() < 3 || name.length() > 15){
            errors.rejectValue("provinceName", "nameProvince.length");
        }
    }
}