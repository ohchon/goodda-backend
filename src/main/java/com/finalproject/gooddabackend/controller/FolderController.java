package com.finalproject.gooddabackend.controller;


import com.finalproject.gooddabackend.dto.ResponseDto;
import com.finalproject.gooddabackend.dto.folder.FolderRequestDto;
import com.finalproject.gooddabackend.dto.folder.FolderResponseDto;
import com.finalproject.gooddabackend.exception.CustomErrorException;
import com.finalproject.gooddabackend.model.Coupon;
import com.finalproject.gooddabackend.model.Folder;
import com.finalproject.gooddabackend.model.User;
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

@RequiredArgsConstructor
@RestController
@Slf4j
@Tag(name = "Cart Controller Api V1")
public class FolderController {

    private final FolderService folderService;
    private final FolderRepository folderRepository;

    // 쿠폰 보관함에 추가
    @Operation(summary = "보관함에 추가")
    @PostMapping("api/folders/create")
    @Transactional
    public ResponseDto addFolder(@Valid @RequestBody FolderRequestDto requestDto, @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("POST, '/api/folders', couponId={}", requestDto.getCouponId());
        if(userDetails == null) {
            throw new CustomErrorException("로그인 사용자만 이용가능합니다.");
        }
        User user = userDetails.getUser();
        Coupon coupon = folderService.loadCoupon(requestDto.getCouponId());
        Folder folder = folderRepository.findAllByUserIdAndCouponId(user.getId(), requestDto.getCouponId());
        if (folder == null) {
            folder = new Folder(user, coupon);
            folderRepository.save(folder);
            Long couponLike = coupon.getCouponLike();
            Long couponLikes = couponLike+1;
            coupon.setCouponLike(couponLikes);
            return new ResponseDto("success", "등록성공");
        }else{
            return new ResponseDto("failed", "이미 찜등록 하셨습니다");
        }
    }

    // 보관함 조회
    @GetMapping("/api/folders/read")
    public FolderResponseDto getFolder(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(userDetails == null) {
            throw new CustomErrorException("로그인 사용자만 이용가능합니다.");
        }
        User user = userDetails.getUser();
        return folderService.getCoupon(user);
        // 쿠폰함에 있는 정보 다 불러오기
    }

    // 보관 정보 삭제
    @DeleteMapping("/api/folders/delete/{couponId}")
    public ResponseDto deleteFolder(@PathVariable Long couponId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        folderService.deleteFolder(user, couponId);
        return new ResponseDto("success", "삭제성공");
    }
}