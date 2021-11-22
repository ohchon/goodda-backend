package com.finalproject.gooddabackend.dto.user;

import com.finalproject.gooddabackend.model.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDataDto {
    String token;
    String userEmail;
    String nickname;
    String telecom;
    String cardType;
    String type1;
    String type2;
    String type3;
    UserRoleEnum role;
    boolean status;
    Long alertCoupon;
}