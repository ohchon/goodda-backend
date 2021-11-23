package com.finalproject.gooddabackend.validator;

import com.finalproject.gooddabackend.dto.user.SignupRequestDto;
import com.finalproject.gooddabackend.exception.CustomErrorException;
import org.springframework.stereotype.Component;

@Component
public class UserVaildator {
    public static void validateUserInput(SignupRequestDto signupRequestDto){
        String userEmail = signupRequestDto.getUserEmail();
        String nickname = signupRequestDto.getNickname();
        String password = signupRequestDto.getPassword();

        if (userEmail.contains("script") || userEmail.contains("<" ) || userEmail.contains(">" )){
            throw new CustomErrorException("보안상 쓸 수 없는 이메일입니다.");
        }
        if (nickname.contains("script") || nickname.contains("<" ) || nickname.contains(">" )){
            throw new CustomErrorException("보안상 쓸 수 없는 닉네임입니다.");
        }
        if (password.contains("script") || password.contains("<" ) || password.contains(">" )){
            throw new CustomErrorException("보안상 쓸 수 없는 비밀번호입니다.");
        }
    }
    public static void validateUserInput2(String nickname){
        if (nickname.contains("script") || nickname.contains("<" ) || nickname.contains(">" )){
            throw new CustomErrorException("보안상 쓸 수 없는 닉네임입니다.");
        }
    }
}
