package com.finalproject.gooddabackend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserUpdateResponseDto {
    String nickname;
    String telecom;
    String cardType;
    String type1;
    String type2;
    String type3;
}