package com.finalproject.gooddabackend.dto.coupon;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.finalproject.gooddabackend.model.Coupon;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CouponMainResponseDto {
    private Long id;
    private String couponBrand;
    private String couponSubTitle;
    private String couponImage;
    private String couponLogo;
    private String couponType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate couponCreate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate couponDespire;
    private Long couponLike;
    private Long couponSelect;

    public CouponMainResponseDto(Coupon coupon, Long couponSelect){
        this.id = coupon.getId();
        this.couponBrand = coupon.getCouponBrand();
        this.couponSubTitle= coupon.getCouponSubTitle();
        this.couponImage = coupon.getCouponImage();
        this.couponLogo = coupon.getCouponLogo();
        this.couponType = coupon.getCouponType();
        this.couponCreate = coupon.getCouponCreate();
        this.couponDespire = coupon.getCouponDespire();
        this.couponLike = coupon.getCouponLike();
        this.couponSelect = couponSelect;
    }

}
