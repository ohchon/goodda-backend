package com.finalproject.gooddabackend.service;

import com.finalproject.gooddabackend.dto.ResponseDto;
import com.finalproject.gooddabackend.dto.user.SignupRequestDto;
import com.finalproject.gooddabackend.dto.user.UserUpdateResponseDto;
import com.finalproject.gooddabackend.exception.CustomErrorException;
import com.finalproject.gooddabackend.model.User;
import com.finalproject.gooddabackend.model.UserRoleEnum;
import com.finalproject.gooddabackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";


    //회원가입
    public ResponseDto signup(SignupRequestDto signupRequestDto) {
        String userEmail = signupRequestDto.getUserEmail();
//        회원 ID 중복 확인
        checkRedunbancy(userEmail);
        //패스워드 암호화
        String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());
        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }
        User user = new User(signupRequestDto, encodedPassword, role, true);
        userRepository.save(user);
        return new ResponseDto("success", "회원가입 성공");
    }

    public void checkRedunbancy(String userEmail) {
        Optional<User> found = userRepository.findByUserEmail(userEmail);
        if (found.isPresent()) {
            throw new CustomErrorException("중복된 유저이메일이 존재합니다.");
        }
    }

    //로그인
    public User login(String userEmail, String password) {
        User user = userRepository.findByUserEmail(userEmail).orElseThrow(
                () -> new CustomErrorException("유저네임을 찾을 수 없습니다.")
        );

        // 패스워드 암호화
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomErrorException("비밀번호가 맞지 않습니다.");
        }
        return user;
    }

    //계정삭제
    @Transactional
    public void delete(Long id) {
//        folderRepository.deleteAllByUserId(id);
//        userRepository.deleteById(id);
        // 유저 존재여부 확인
        User user = userRepository.findById(id).orElseThrow(
                () -> new CustomErrorException("해당 유저를 찾을 수 없어 수정할 수 없습니다."));

        user.statusUser(false);
    }

    //계정활성화
    @Transactional
    public void reactivate(Long id) {
//        folderRepository.deleteAllByUserId(id);
//        userRepository.deleteById(id);
        // 유저 존재여부 확인
        User user = userRepository.findById(id).orElseThrow(
                () -> new CustomErrorException("해당 유저를 찾을 수 없어 수정할 수 없습니다."));

        user.statusUser(true);
    }

    //계정수정
    @Transactional
    public ResponseDto modifyUser(String userEmail, String nickname, String telecom, String cardType, String type1, String type2, String type3) {

        // 유저 존재여부 확인
        User editUser = userRepository.findByUserEmail(userEmail).orElseThrow(
                () -> new CustomErrorException("해당 유저를 찾을 수 없어 수정할 수 없습니다."));

        // 유저 update
//        editUser.set(commentRequestDto.getComment());
        editUser.updateUser(nickname, telecom, cardType, type1, type2, type3);

        UserUpdateResponseDto userUpdateResponseDto = new UserUpdateResponseDto(
                editUser.getNickname(),
                editUser.getTelecom(),
                editUser.getCardType(),
                editUser.getType1(),
                editUser.getType2(),
                editUser.getType3()
        );

        return new ResponseDto("success", userUpdateResponseDto);
    }
}

