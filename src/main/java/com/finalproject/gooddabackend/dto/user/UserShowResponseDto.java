package com.finalproject.gooddabackend.dto.user;

import com.finalproject.gooddabackend.model.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserShowResponseDto {
    String nickname;
    String telecom;
    String cardType;
    String type1;
    String type2;
    String type3;
    UserRoleEnum role;
}
