//package com.finalproject.gooddabackend.mockobject;
//
//import com.finalproject.gooddabackend.dto.ResponseDto;
//import com.finalproject.gooddabackend.dto.coupon.CouponUpdateRequestDto;
//import com.finalproject.gooddabackend.repository.CouponRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@ExtendWith(MockitoExtension.class)
//class MockCouponServiceTest {
//
//    @Mock
//    CouponRepository couponRepository;
//
//    @Test
//    @DisplayName("쿠폰수정")
//    void updateCoupon_Normal() {
//        //given
//        Long couponId = 1L;
//        String couponBrand = "브랜드";
//        String couponTitle = "할인제목";
//        String couponSubTitle = "할인부제목";
//        MultipartFile couponImage = new MockMultipartFile("file", "imagefile.jpeg", "image/jpeg", "<<jpeg data>>".getBytes());
//        String couponImage1 = "https://good-da-bucket.s3.ap-northeast-2.amazonaws.com/image/f2089435-b3fa-4ef5-a2cc-9a3d3648f261S3%E1%84%83%E1%85%A5%E1%84%86%E1%85%B5%E1%84%83%E1%85%A6%E1%84%8B%E1%85%B5%E1%84%90%E1%85%A5.png";
//        String  couponLogo = "https://good-da-bucket.s3.ap-northeast-2.amazonaws.com/image/f2089435-b3fa-4ef5-a2cc-9a3d3648f261S3%E1%84%83%E1%85%A5%E1%84%86%E1%85%B5%E1%84%83%E1%85%A6%E1%84%8B%E1%85%B5%E1%84%90%E1%85%A5.png" ;
//        String couponType = "할인타입";
//        String couponDesc = "할인설명";
//        String couponUrl = "https://www.naver.com/";
//        LocalDate couponCreate = LocalDate.of(2021, 8, 9);
//        LocalDate couponDespire = LocalDate.of(2021, 10, 10);
//        Long couponLike = 0L;
//        CouponUpdateRequestDto couponUpdateRequestDto = new CouponUpdateRequestDto(
//                couponBrand,
//                couponTitle,
//                couponSubTitle,
//                couponImage,
//                couponLogo,
//                couponType,
//                couponDesc,
//                couponUrl,
//                couponCreate,
//                couponDespire
//        );
//    }
//}