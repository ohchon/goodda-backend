package com.finalproject.gooddabackend.validator;

import com.finalproject.gooddabackend.dto.coupon.CouponCreateRequestDto;
import org.springframework.stereotype.Component;

import static com.finalproject.gooddabackend.validator.URLValidator.isValidUrl;

@Component
public class CouponVaildator {
    public static void validateCouponInput(CouponCreateRequestDto couponCreateRequestDto, String couponImage){
        if (!isValidUrl(couponImage) ){
            throw new IllegalArgumentException("이미지 URL 포맷이 맞지 않습니다.");
        }
        if (couponCreateRequestDto.getCouponBrand()==null || couponCreateRequestDto.getCouponBrand().isEmpty()){
            throw new IllegalArgumentException("저장할 수 있는 브랜드명이 없습니다.");
        }
        if (couponCreateRequestDto.getCouponTitle()==null || couponCreateRequestDto.getCouponTitle().isEmpty()){
            throw new IllegalArgumentException("저장할 수 있는 할인제목이 없습니다.");
        }
        if (couponCreateRequestDto.getCouponSubTitle()==null || couponCreateRequestDto.getCouponSubTitle().isEmpty()){
            throw new IllegalArgumentException("저장할 수 있는 할인부제목이 없습니다.");
        }
        if (couponCreateRequestDto.getCouponImage().isEmpty()){
            throw new IllegalArgumentException("저장할 수 있는 이미지가 없습니다.");
        }
        if (!isValidUrl(couponCreateRequestDto.getCouponLogo())) {
            throw new IllegalArgumentException("로고 URL 포맷이 맞지 않습니다.");
        }
        if (couponCreateRequestDto.getCouponType()==null || couponCreateRequestDto.getCouponType().isEmpty()){
            throw new IllegalArgumentException("저장할 수 있는 타입명이 없습니다.");
        }
        if (couponCreateRequestDto.getCouponDesc()==null || couponCreateRequestDto.getCouponDesc().isEmpty()){
            throw new IllegalArgumentException("저장할 수 있는 설명이 없습니다.");
        }
        if (!isValidUrl(couponCreateRequestDto.getCouponUrl())) {
            throw new IllegalArgumentException("상품 최저가 페이지 URL 포맷이 맞지 않습니다.");
        }
    }
}
