package com.finalproject.gooddabackend.controller;



import com.finalproject.gooddabackend.dto.ResponseDto;
import com.finalproject.gooddabackend.dto.user.*;
import com.finalproject.gooddabackend.exception.CustomErrorException;
import com.finalproject.gooddabackend.jwt.JwtTokenProvider;
import com.finalproject.gooddabackend.model.User;
import com.finalproject.gooddabackend.model.UserRoleEnum;
import com.finalproject.gooddabackend.repository.UserRepository;
import com.finalproject.gooddabackend.security.UserDetailsImpl;
import com.finalproject.gooddabackend.service.CouponService;
import com.finalproject.gooddabackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CouponService couponService;

    //회원가입
    @PostMapping("/api/user/signup")
    public ResponseDto signUp(@RequestBody SignupRequestDto signupRequestDto) {
        System.out.println("UserController:"+signupRequestDto.getNickname());
        return userService.signup(signupRequestDto);
    }

    // 로그인
    @PostMapping("/api/user/login")
    public ResponseDto login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        User user = userService.login(requestDto.getUserEmail(), requestDto.getPassword());
        String checkedUserEmail = user.getUserEmail();
        String token = jwtTokenProvider.createToken(checkedUserEmail);
        String checkedNickname = user.getNickname();
        String checkedTelecom = user.getTelecom();
        String checkedCardType = user.getCardType();
        String checkedType1 = user.getType1();
        String checkedType2 = user.getType2();
        String checkedType3 = user.getType3();
        UserRoleEnum role = user.getRole();
        boolean status = user.isStatus();

       Long alertCoupon = couponService.couponAlert(user);


        UserResponseDataDto dataDto = new UserResponseDataDto(token,checkedUserEmail,checkedNickname, checkedTelecom, checkedCardType, checkedType1, checkedType2, checkedType3, role, status, alertCoupon);

        response.setHeader("X-AUTH-TOKEN", token);
        Cookie cookie = new Cookie("X-AUTH-TOKEN", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);

        //body에도 보내주기 혹시모르니까
        return new ResponseDto("success",dataDto);
    }
    //유저이름 중복확인
    @PostMapping("/api/user/redunancy")
    public ResponseDto checkRedunancy(@RequestBody UserEmailDto username){
        System.out.println("유저네임: "+username.getUserEmail());
        userService.checkRedunbancy(username.getUserEmail());

        return new ResponseDto("success","중복되지 않습니다");
    }



    //계정삭제
    @PutMapping("/api/user/delete")
    public ResponseDto deleteUser(
            @RequestBody UserDeleteRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        checkLogin(userDetails);
        // 패스워드 체크
        if (!passwordEncoder.matches(requestDto.getPassword(), userDetails.getPassword())) {
            throw new CustomErrorException("비밀번호가 맞지 않습니다.");
        }
        userService.delete(userDetails.getUser().getId());
        return new ResponseDto("success", "계정이 삭제되었습니다");
    }

    //계정수정
    @PutMapping("/api/user/change")
    public ResponseDto changeUser(
            @RequestBody UserUpdateRequestDto requestDto,
            @AuthenticationPrincipal @ApiIgnore UserDetailsImpl userDetails
    ) {
        checkLogin(userDetails);
        // 패스워드 체크
        if (!passwordEncoder.matches(requestDto.getPassword(), userDetails.getPassword())) {
            throw new CustomErrorException("비밀번호가 맞지 않습니다.");
        }
        return userService.modifyUser(
                userDetails.getUsername(),
                requestDto.getNickname(),
                requestDto.getTelecom(),
                requestDto.getCardType(),
                requestDto.getType1(),
                requestDto.getType2(),
                requestDto.getType3());
    }
    //로그인 체크
    private void checkLogin(UserDetailsImpl userDetails) {
        if(userDetails == null){
            throw new CustomErrorException("로그인 사용자만 이용가능합니다.");
        }
    }

    //유저정보 보여주기
    @GetMapping("/api/user/show")
    public ResponseDto showUser(@AuthenticationPrincipal @ApiIgnore UserDetailsImpl userDetails)
    {
        checkLogin(userDetails);

        String userEmail =  userDetails.getUsername();
        // 유저 존재여부 확인
        User showUser = userRepository.findByUserEmail(userEmail).orElseThrow(
                () -> new CustomErrorException("해당 유저를 찾을 수 없습니다."));

        UserShowResponseDto userShowResponseDto = new UserShowResponseDto(
                showUser.getNickname(),
                showUser.getTelecom(),
                showUser.getCardType(),
                showUser.getType1(),
                showUser.getType2(),
                showUser.getType3(),
                showUser.getRole()
        );
        return new ResponseDto("success",userShowResponseDto);
    }

//유저활성화
    @PutMapping("api/user/reactivation")
    public ResponseDto reactivateUser(@AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        userService.reactivate(userDetails.getUser().getId());
        return new ResponseDto("success", "계정이 활성화되었습니다");
    }
}
