package com.finalproject.gooddabackend.model;

import com.finalproject.gooddabackend.dto.coupon.CouponCreateRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CouponTest {
    @Test
    @DisplayName("정상 케이스")
    void createCoupon_Normal(){

        // given
         String couponBrand = "브랜드";
         String couponTitle = "할인제목";
         String couponSubTitle = "할인부제목";
         MockMultipartFile couponImage = new MockMultipartFile("file", "imagefile.jpeg", "image/jpeg", "<<jpeg data>>".getBytes());
         String couponLogo = "브랜드로고";
         String couponType = "할인타입";
         String couponDesc = "할인설명";
         String couponUrl = "할인레퍼럴";
         LocalDate couponCreate = LocalDate.of(2021, 8, 9);
         LocalDate couponDespire = LocalDate.of(2021, 10, 10);
         Long couponLike = 0L;

         CouponCreateRequestDto couponCreateRequestDto = new CouponCreateRequestDto(
                 couponBrand,
                 couponTitle,
                 couponSubTitle,
                 couponImage,
                 couponLogo,
                 couponType,
                 couponDesc,
                 couponUrl,
                 couponCreate,
                 couponDespire
         );

         //사진업로드 후 주소가져오기
        // String couponImageAddress = s3Uploader.upload(couponCreateRequestDto.getCouponImage(), "image");
        //다시알아봐야함
        String couponImageAddress = "이미지주소";

        // when
         Coupon coupon = new Coupon(couponCreateRequestDto, couponLike, couponImageAddress);

         //then
        assertNull(coupon.getId());
        assertEquals(couponBrand, coupon.getCouponBrand());
        assertEquals(couponTitle, coupon.getCouponTitle());
        assertEquals(couponSubTitle, coupon.getCouponSubTitle());
        assertEquals(couponImageAddress, coupon.getCouponImage());
        assertEquals(couponLogo, coupon.getCouponLogo());
        assertEquals(couponType, coupon.getCouponType());
        assertEquals(couponDesc, coupon.getCouponDesc());
        assertEquals(couponUrl, coupon.getCouponUrl());
        assertEquals(couponCreate, coupon.getCouponCreate());
        assertEquals(couponDespire, coupon.getCouponDespire());
        assertEquals(couponLike, coupon.getCouponLike());
     }
}