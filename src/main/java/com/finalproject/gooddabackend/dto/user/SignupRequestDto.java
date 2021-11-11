package com.finalproject.gooddabackend.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class    SignupRequestDto {
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
}