package com.finalproject.gooddabackend.controller;


import com.finalproject.gooddabackend.dto.ResponseDto;
import com.finalproject.gooddabackend.dto.coupon.CouponCreateRequestDto;
import com.finalproject.gooddabackend.dto.coupon.CouponUpdateRequestDto;
import com.finalproject.gooddabackend.exception.CustomErrorException;
import com.finalproject.gooddabackend.model.Coupon;
import com.finalproject.gooddabackend.model.UserRoleEnum;
import com.finalproject.gooddabackend.repository.CouponRepository;
import com.finalproject.gooddabackend.security.UserDetailsImpl;
import com.finalproject.gooddabackend.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CouponController {

    private final CouponRepository couponRepository;
    private final CouponService couponService;

    // 쿠폰 리스트 타입별로
    @GetMapping("/api/main/{couponType}")
    public ResponseDto couponList(@PathVariable String couponType,
                                  @RequestParam("page") int page,
                                  @RequestParam("size") int size,
                                  @RequestParam("sortBy") String sortBy,
                                  @RequestParam("isAsc") boolean isAsc,
                                  @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long userId = userDetails.getUser().getId();
        page = page - 1;
        return couponService.responseList1(couponType, userId, page, size, sortBy, isAsc);
    }


    // 해당 id 쿠폰 주기
    @GetMapping("/api/detail/{couponId}")
    public ResponseDto idCoupon(@PathVariable Long couponId) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(()->
                new CustomErrorException("해당 할인정보를 찾을 수 없습니다."));
        return new ResponseDto("success", coupon);
    }
    //쿠폰순위
    @GetMapping("/api/main/rank")
    public ResponseDto rankCoupon(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                  @RequestParam("page") int page,
                                  @RequestParam("size") int size) {
        page = page - 1;
        LocalDate now = LocalDate.now();
        Pageable pageable = PageRequest.of(page, size);

        Page<Coupon> couponList = couponRepository.findAllByCouponDespireAfterOrderByCouponLikeDesc(now, pageable);

        if(userDetails == null) {
            return couponService.responseRankList(couponList);
        } else {
            Long userId = userDetails.getUser().getId();
            return couponService.showList(userId, couponList);
        }
    }


    // (관리자용) 쿠폰 리스트
    @Secured(value = UserRoleEnum.Authority.ADMIN)
    @GetMapping("/api/admin/main")
    public ResponseDto couponList() {
        List<Coupon> coupon = couponRepository.findAll();
        return new ResponseDto("success", coupon);
    }

    // (관리자용) 상품등록
    @Secured(value = UserRoleEnum.Authority.ADMIN)
    @PostMapping("/api/admin/coupon")
    public ResponseDto createCoupon(CouponCreateRequestDto couponCreateRequestDto) throws IOException {
        return couponService.createCoupon(couponCreateRequestDto);
    }

    // (관리자용) 상품삭제
    @Secured(value = UserRoleEnum.Authority.ADMIN)
    @DeleteMapping("/api/admin/coupon/{couponId}")
    public ResponseDto deleteCoupon(@PathVariable Long couponId) {
        return couponService.deleteCoupon(couponId);
    }

    // (관리자용) 상품수정
    @Secured(value = UserRoleEnum.Authority.ADMIN)
    @PutMapping("api/admin/coupon/update/{couponId}")
    public ResponseDto updateCoupon(@PathVariable Long couponId, CouponUpdateRequestDto couponUpdateRequestDto) throws IOException {
        return couponService.updateCoupon(couponId, couponUpdateRequestDto);
    }
}