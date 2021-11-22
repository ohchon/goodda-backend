package com.finalproject.gooddabackend.model;

import com.finalproject.gooddabackend.dto.coupon.CouponCreateRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CouponTest {
    @Nested
    @DisplayName("관리자가 요청한 할인정보 객체 생성")
    class CreateCoupon {
        private String couponBrand;
        private String couponTitle;
        private String couponSubTitle;
        private MockMultipartFile couponImage;
        private String couponImage1;
        private String couponLogo;
        private String couponType;
        private String couponDesc;
        private String couponUrl;
        private LocalDate couponCreate;
        private LocalDate couponDespire;
        private Long couponLike;

        @BeforeEach
        void setup() {
             couponBrand = "브랜드";
             couponTitle = "할인제목";
             couponSubTitle = "할인부제목";
             couponImage = new MockMultipartFile("file", "imagefile.jpeg", "image/jpeg", "<<jpeg data>>".getBytes());
             couponImage1 = "https://good-da-bucket.s3.ap-northeast-2.amazonaws.com/image/f2089435-b3fa-4ef5-a2cc-9a3d3648f261S3%E1%84%83%E1%85%A5%E1%84%86%E1%85%B5%E1%84%83%E1%85%A6%E1%84%8B%E1%85%B5%E1%84%90%E1%85%A5.png";
             couponLogo = "https://good-da-bucket.s3.ap-northeast-2.amazonaws.com/image/f2089435-b3fa-4ef5-a2cc-9a3d3648f261S3%E1%84%83%E1%85%A5%E1%84%86%E1%85%B5%E1%84%83%E1%85%A6%E1%84%8B%E1%85%B5%E1%84%90%E1%85%A5.png" ;
             couponType = "할인타입";
             couponDesc = "할인설명";
             couponUrl = "https://www.naver.com/";
             couponCreate = LocalDate.of(2021, 8, 9);
             couponDespire = LocalDate.of(2021, 10, 10);
             couponLike = 0L;
        }

        @Test
        @DisplayName("정상 케이스")
        void createCoupon_Normal() {
//given
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
            //String couponImageAddress = "이미지주소";

            // when
            Coupon coupon = new Coupon(couponCreateRequestDto, couponLike, couponImage1);

            //then
            assertNull(coupon.getId());
            assertEquals(couponBrand, coupon.getCouponBrand());
            assertEquals(couponTitle, coupon.getCouponTitle());
            assertEquals(couponSubTitle, coupon.getCouponSubTitle());
            assertEquals(couponImage1, coupon.getCouponImage());
            assertEquals(couponLogo, coupon.getCouponLogo());
            assertEquals(couponType, coupon.getCouponType());
            assertEquals(couponDesc, coupon.getCouponDesc());
            assertEquals(couponUrl, coupon.getCouponUrl());
            assertEquals(couponCreate, coupon.getCouponCreate());
            assertEquals(couponDespire, coupon.getCouponDespire());
            assertEquals(couponLike, coupon.getCouponLike());
        }
        @Nested
        @DisplayName("실패 케이스")
        class FailCases {
            @Nested
            @DisplayName("할인브랜드")
                class CouponBrand{
                @Test
                @DisplayName("null")
                void fail1() {
                    // given
                    couponBrand = null;
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
                    // when
                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        new Coupon(couponCreateRequestDto, couponLike, couponImage1);
                    });

                    // then
                    assertEquals("저장할 수 있는 브랜드명이 없습니다.", exception.getMessage());
                }
                @Test
                @DisplayName("빈 문자열")
                void fail2() {
                    // given
                    String couponBrand = "";

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
                    // when
                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        new Coupon(couponCreateRequestDto, couponLike, couponImage1);
                    });

                    // then
                    assertEquals("저장할 수 있는 브랜드명이 없습니다.", exception.getMessage());
                }
            }

            @Nested
            @DisplayName("쿠폰로고URL")
            class couponLogo {
                @Test
                @DisplayName("null")
                void fail1() {
                    // given
                    couponLogo = "https";

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

                    // when
                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        new Coupon(couponCreateRequestDto, couponLike, couponImage1);
                    });

                    // then
                    assertEquals("로고 URL 포맷이 맞지 않습니다.", exception.getMessage());
                }

                @Test
                @DisplayName("URL 포맷 형태가 맞지 않음")
                void fail2() {
                    // given
                    couponLogo = "shopping-phinf.pstatic.net/main_2416122/24161228524.20200915151118.jpg";

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

                    // when
                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        new Coupon(couponCreateRequestDto, couponLike, couponImage1);
                    });

                    // then
                    assertEquals("로고 URL 포맷이 맞지 않습니다.", exception.getMessage());
                }
            }
        }
    }
}