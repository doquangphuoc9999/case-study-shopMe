package com.codegym.model;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.Where;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Where(clause = "deleted=false")
@Transactional
public class User implements Validator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    @ManyToOne(fetch = FetchType.LAZY)
    private Province province;


    @OneToMany(mappedBy = "user")
    List<Order> list = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<Order> listOder = new ArrayList<>();

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        String userName = user.getNameUser();
        String gmailUser = user.getGmailUser();
        String nickName = user.getNickname();
        String password = user.getPassWordUser();

        ValidationUtils.rejectIfEmpty(errors,"nameUser","userName.empty");
        ValidationUtils.rejectIfEmpty(errors,"gmailUser","gmailUser.empty");
        ValidationUtils.rejectIfEmpty(errors,"nickname","nickNameUser.empty");
        ValidationUtils.rejectIfEmpty(errors,"passWordUser","passwordUser.empty");

        if (userName.length() > 50 || userName.length() < 2){
            errors.rejectValue("nameUser","nameUser.length");
        }
        if (gmailUser.length() > 50 || gmailUser.length() < 10 ){
            errors.rejectValue("gmailUser","gmailUser.length");
        }
        if (nickName.length() > 20 || nickName.length() < 3){
            errors.rejectValue("nickname", "nickName.length");
        }
        if (!gmailUser.matches("^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$")){
            errors.rejectValue("gmailUser","gmailUser.matches");
        }
        if (!userName.matches("(^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s\\W|_]+$)")){
            errors.rejectValue("nameUser", "nameUser.matches");
        }
    }
}
