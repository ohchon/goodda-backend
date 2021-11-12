package com.finalproject.gooddabackend.dto.coupon;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class CouponUpdateRequestDto {
    @NotBlank
    private String couponBrand;
    @NotBlank
    private String couponTitle;
    @NotBlank
    private String couponSubTitle;

    private MultipartFile couponImage;

    @NotBlank
    private String couponLogo;
    @NotBlank
    private String couponType;
    @NotBlank
    private String couponDesc;
    @NotBlank
    private String couponUrl;
    @NotBlank
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate couponCreate;
    @NotBlank
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate couponDespire;
}

