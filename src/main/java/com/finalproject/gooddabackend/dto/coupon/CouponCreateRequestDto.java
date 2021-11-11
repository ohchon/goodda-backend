package com.finalproject.gooddabackend.dto.coupon;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class CouponCreateRequestDto {
    private String couponBrand;
    private String couponTitle;
    private String couponSubTitle;
    private MultipartFile couponImage;
    private String couponLogo;
    private String couponType;
    private String couponDesc;
    private String couponUrl;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate couponCreate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate couponDespire;
}
