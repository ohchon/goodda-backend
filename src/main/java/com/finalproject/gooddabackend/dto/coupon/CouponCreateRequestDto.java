package com.finalproject.gooddabackend.dto.coupon;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class CouponCreateRequestDto {
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

    //테스트 코드용
    public CouponCreateRequestDto(String couponBrand, String couponTitle, String couponSubTitle, MultipartFile couponImage, String couponLogo, String couponType, String couponDesc, String couponUrl, LocalDate couponCreate, LocalDate couponDespire) {
        this.couponBrand = couponBrand;
        this.couponTitle = couponTitle;
        this.couponSubTitle= couponSubTitle;
        this.couponImage = couponImage;
        this.couponLogo = couponLogo;
        this.couponType = couponType;
        this.couponDesc = couponDesc;
        this.couponUrl = couponUrl;
        this.couponCreate = couponCreate;
        this.couponDespire = couponDespire;
    }
}
