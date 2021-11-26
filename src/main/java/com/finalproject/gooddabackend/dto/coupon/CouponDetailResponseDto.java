package com.finalproject.gooddabackend.dto.coupon;

import com.finalproject.gooddabackend.model.Coupon;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class CouponDetailResponseDto {
    private Long id;
    private String couponBrand;
    private String couponTitle;
    private String couponSubTitle;
    private String couponImage;
    private String couponLogo;
    private String couponType;
    private String couponDesc;
    private String couponUrl;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate couponCreate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate couponDespire;
    private Long couponLike;
    private Long couponSelect;

    public CouponDetailResponseDto(Coupon coupon, Long couponSelect){
        this.id = coupon.getId();
        this.couponBrand = coupon.getCouponBrand();
        this.couponTitle = coupon.getCouponTitle();
        this.couponSubTitle= coupon.getCouponSubTitle();
        this.couponImage = coupon.getCouponImage();
        this.couponLogo = coupon.getCouponLogo();
        this.couponType = coupon.getCouponType();
        this.couponDesc = coupon.getCouponDesc();
        this.couponUrl = coupon.getCouponUrl();
        this.couponCreate = coupon.getCouponCreate();
        this.couponDespire = coupon.getCouponDespire();
        this.couponLike = coupon.getCouponLike();
        this.couponSelect = couponSelect;
    }
}

