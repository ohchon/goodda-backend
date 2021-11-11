package com.finalproject.gooddabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    public User(String userEmail, String nickname, String password, String telecom, String cardType, String type1, String type2, String type3, UserRoleEnum role){
        this.userEmail = userEmail;
        this.nickname = nickname;
        this.password = password;
        this.telecom = telecom;
        this.cardType = cardType;
        this.type1 = type1;
        this.type2 = type2;
        this.type3 = type3;
        this.role = role;
    }
    public void updateUser(String nickname, String telecom, String cardType, String type1, String type2, String type3){
        this.nickname = nickname;
        this.telecom = telecom;
        this.cardType = cardType;
        this.type1 = type1;
        this.type2 = type2;
        this.type3 = type3;
    }
}
