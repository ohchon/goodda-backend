package com.finalproject.gooddabackend.controller;


import com.finalproject.gooddabackend.dto.ResponseDto;
import com.finalproject.gooddabackend.dto.folder.FolderRequestDto;
import com.finalproject.gooddabackend.dto.folder.FolderResponseDto;
import com.finalproject.gooddabackend.exception.CustomErrorException;
import com.finalproject.gooddabackend.model.Coupon;
import com.finalproject.gooddabackend.model.Folder;
import com.finalproject.gooddabackend.model.User;
import com.finalproject.gooddabackend.repository.CouponRepository;
import com.finalproject.gooddabackend.repository.FolderRepository;
import com.finalproject.gooddabackend.security.UserDetailsImpl;
import com.finalproject.gooddabackend.service.FolderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@Slf4j
@Tag(name = "Cart Controller Api V1")
public class FolderController {

    private final FolderService folderService;
    private final FolderRepository folderRepository;
    private final CouponRepository couponRepository;

    // 쿠폰 보관함에 추가
    @Operation(summary = "보관함에 추가")
    @PostMapping("api/folders")
    @Transactional
    public ResponseDto addFolder(@Valid @RequestBody FolderRequestDto requestDto, @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("POST, '/api/folders', couponId={}", requestDto.getCouponId());
        if(userDetails == null) {
            throw new CustomErrorException("로그인 사용자만 이용가능합니다.");
        }
        User user = userDetails.getUser();
        Coupon coupon = folderService.loadCoupon(requestDto.getCouponId());
        LocalDate couponDespire = coupon.getCouponDespire();
        Folder folder = folderRepository.findAllByUserIdAndCouponId(user.getId(), requestDto.getCouponId());
        if (folder == null) {
            folder = new Folder();
            folder.addNewFolder(user, coupon, couponDespire);
        }
        folderRepository.save(folder);

        //쿠폰라이크 숫자 바꾸기
        Long couponId = requestDto.getCouponId();
        Coupon editCoupon = couponRepository.findById(couponId).orElseThrow(
                () -> new CustomErrorException("해당 쿠폰을 찾을 수 없어 수정할 수 없습니다."));
        Long couponLikes = folderRepository.countByCouponId(couponId);
//        Long couponLike = editCoupon.getCouponLike();
//        Long couponLikes = couponLike+1;
        editCoupon.setCouponLike(couponLikes);
        return new ResponseDto("success", "added");
    }

    // 보관함 조회
    @GetMapping("/api/folders")
    public ResponseDto getFolder(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(userDetails == null) {
            throw new CustomErrorException("로그인 사용자만 이용가능합니다.");
        }
        User user = userDetails.getUser();
        FolderResponseDto responseDto = folderService.getCoupon(user);
        return new ResponseDto("success", responseDto);

        // 쿠폰함에 있는 정보 다 불러오기
    }

    // 보관 정보 삭제
    @DeleteMapping("/api/folders/{couponId}")
    public ResponseDto deleteFolder(@PathVariable Long couponId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        folderService.deleteFolder(user, couponId);
        return new ResponseDto("success", "");
    }
}