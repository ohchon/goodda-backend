package com.finalproject.gooddabackend.model;

import com.finalproject.gooddabackend.dto.user.SignupRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("정상 케이스")
    void createUser_Normal() {
        String userEmail = "유저이메일";
        String nickname = "유저닉네임";
        String password = "유저비밀번호";
        String telecom = "통신사";
        String cardType = "카드사";
        String type1 = "타입1";
        String type2 = "타입2";
        String type3 = "타입3";
        boolean admin = false;
        String adminToken = "";

        SignupRequestDto signupRequestDto = new SignupRequestDto(
                userEmail,
                nickname,
                password,
                telecom,
                cardType,
                type1,
                type2,
                type3,
                admin,
                adminToken
        );

        //패스워드 인코더로 비밀번호 수정하는 부분 있어야함
        //생성자를 추가해야하는데 아직은 알아봐야함

        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals("AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC")) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }
        boolean status = true;

        User user = new User(signupRequestDto, password, role, status);

        assertNull(user.getId());
        assertEquals(userEmail, user.getUserEmail());
        assertEquals(nickname, user.getNickname());
        assertEquals(password, user.getPassword());
        assertEquals(telecom, user.getTelecom());
        assertEquals(cardType, user.getCardType());
        assertEquals(type1, user.getType1());
        assertEquals(type2, user.getType2());
        assertEquals(type3, user.getType3());
        assertEquals(role, user.getRole());
        assertEquals(status, user.isStatus());
    }
}