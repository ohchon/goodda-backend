package com.finalproject.gooddabackend.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupRequestDto {
    private String userEmail;
    private String nickname;
    private String password;
    private String telecom;
    private String cardType;
    private String type1;
    private String type2;
    private String type3;
    private boolean admin = false;
    private String adminToken = "";

    //테스트코드용
    public SignupRequestDto(String userEmail, String nickname, String password, String telecom, String cardType, String type1, String type2, String type3, boolean admin, String adminToken){
        this.userEmail = userEmail;
        this.nickname = nickname;
        this.password = password;
        this.telecom = telecom;
        this.cardType = cardType;
        this.type1 = type1;
        this.type2 = type2;
        this.type3 =type3;
        this.admin = admin;
        this.adminToken = adminToken;
    }
}