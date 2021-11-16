package com.finalproject.gooddabackend.dto.coupon;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CouponMainResponseDto {
    private Long id;
    private String couponBrand;
    private String couponSubTitle;
    private String couponLogo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate couponCreate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate couponDespire;
    private Long couponLike;
    private Long couponSelect;

}
