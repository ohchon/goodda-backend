package com.finalproject.gooddabackend.dto.coupon;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.finalproject.gooddabackend.model.Coupon;
import com.finalproject.gooddabackend.model.Folder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CouponResponseDto implements Comparable<CouponResponseDto> {

    private Long id;

    private String couponBrand;

    private String couponSubTitle;

    private String couponLogo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate couponCreate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate couponDespire;

    public CouponResponseDto(Coupon coupon) {
        this.id = coupon.getId();
        this.couponBrand = coupon.getCouponBrand();
        this.couponSubTitle = coupon.getCouponSubTitle();
        this.couponLogo = coupon.getCouponLogo();
        this.couponCreate = coupon.getCouponCreate();
        this.couponDespire = coupon.getCouponDespire();
    }
@Override
public int compareTo(CouponResponseDto o) {
        return getCouponDespire().compareTo(o.getCouponDespire());
        }
}
