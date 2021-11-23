package com.finalproject.gooddabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finalproject.gooddabackend.dto.user.SignupRequestDto;
import com.finalproject.gooddabackend.validator.UserVaildator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Setter
@Entity
@Getter
@NoArgsConstructor //기본생성자 만들어줌
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    private Long id;

    @Column(nullable = false, unique = true)
    private String userEmail;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String telecom;

    @Column(nullable = false)
    private String cardType;

    @Column(nullable = false)
    private String type1;

    @Column(nullable = false)
    private String type2;

    @Column(nullable = false)
    private String type3;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Folder> FolderList = new ArrayList<>();

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Column(nullable = false)
    private boolean status;


//    public User(String userEmail, String nickname, String password, String telecom, String cardType, String type1, String type2, String type3, UserRoleEnum role, boolean status){
//        this.userEmail = userEmail;
//        this.nickname = nickname;
//        this.password = password;
//        this.telecom = telecom;
//        this.cardType = cardType;
//        this.type1 = type1;
//        this.type2 = type2;
//        this.type3 = type3;
//        this.role = role;
//        this.status = status;
//    }

    public User(SignupRequestDto signupRequestDto, String password, UserRoleEnum role, boolean status){
        UserVaildator.validateUserInput(signupRequestDto);

        this.userEmail = signupRequestDto.getUserEmail();
        this.nickname = signupRequestDto.getNickname();
        this.password = password;
        this.telecom = signupRequestDto.getTelecom();
        this.cardType = signupRequestDto.getCardType();
        this.type1 = signupRequestDto.getType1();
        this.type2 = signupRequestDto.getType2();
        this.type3 = signupRequestDto.getType3();
        this.role = role;
        this.status = status;
    }
    public void updateUser(String nickname, String telecom, String cardType, String type1, String type2, String type3){
        UserVaildator.validateUserInput2(nickname);

        this.nickname = nickname;
        this.telecom = telecom;
        this.cardType = cardType;
        this.type1 = type1;
        this.type2 = type2;
        this.type3 = type3;
    }
    public void statusUser(boolean status){
        this.status = status;
    }
}
