//package com.finalproject.gooddabackend.mockobject;
//
//import com.amazonaws.AmazonServiceException;
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.finalproject.gooddabackend.awsS3.S3Uploader;
//import com.finalproject.gooddabackend.dto.ResponseDto;
//import com.finalproject.gooddabackend.dto.coupon.CouponCreateRequestDto;
//import com.finalproject.gooddabackend.dto.coupon.CouponMainResponseDto;
//import com.finalproject.gooddabackend.dto.coupon.CouponRankResponseDto;
//import com.finalproject.gooddabackend.dto.coupon.CouponUpdateRequestDto;
//import com.finalproject.gooddabackend.exception.CustomErrorException;
//import com.finalproject.gooddabackend.model.Coupon;
//import com.finalproject.gooddabackend.model.Folder;
//import com.finalproject.gooddabackend.repository.CouponRepository;
//import com.finalproject.gooddabackend.repository.FolderRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import javax.transaction.Transactional;
//import java.io.IOException;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class MockCouponService {
//
//    private final MockCouponRepository mockCouponRepository;
//    private final FolderRepository folderRepository;
//    private final S3Uploader s3Uploader;
//    private final AmazonS3Client amazonS3Client;
//    private final String bucket = "good-da-bucket";//손보긴 해야함
////
////    public MockCouponService() {
////        this.mockCouponRepository = new MockCouponRepository();
////    }
//
//
//    //쿠폰생성(관리자)
//    public ResponseDto createCoupon(CouponCreateRequestDto couponCreateRequestDto) throws IOException {
//        Long couponLike = 0L;
//        if (couponCreateRequestDto.getCouponImage() != null) {
//            String couponImage = s3Uploader.upload(couponCreateRequestDto.getCouponImage(), "image");
//            Coupon coupon = new Coupon(couponCreateRequestDto, couponLike, couponImage);
//            mockCouponRepository.save(coupon);
//        } else {
//            String couponImage = "";
//            Coupon coupon = new Coupon(couponCreateRequestDto, couponLike, couponImage);
//            mockCouponRepository.save(coupon);
//        }
//        return new ResponseDto("success", "쿠폰생성 성공");
//    }
//
//    //쿠폰삭제(관리자)
//    @Transactional
//    public ResponseDto deleteCoupon(Long couponId) {
//        Coupon coupon = mockCouponRepository.findById(couponId).orElseThrow(
//                () -> new CustomErrorException("해당 쿠폰을 찾을 수 없어 삭제할 수 없습니다."));
//        System.out.println("S3삭제시작");
//        deleteS3(coupon.getCouponImage());
//        System.out.println("S3삭제성공");
//        folderRepository.deleteAllByCouponId(couponId);
//        mockCouponRepository.deleteById(couponId);
//        //원래는 아이디로 데이터 찾아서 거기 이미지 S3주소에 있는 이미지까지 삭제
//        return new ResponseDto("success", "쿠폰삭제 성공");
//    }
//
//    //S3 삭제
//    public void deleteS3(@RequestParam String imageName) {
//        //https://S3 버킷 URL/버킷에 생성한 폴더명/이미지이름
//        String keyName = imageName.split("/")[4]; // 이미지이름만 추출
//
//        try {amazonS3Client.deleteObject(bucket + "/image", keyName);
//        } catch (AmazonServiceException e) {
//            e.printStackTrace();
//            throw new AmazonServiceException(e.getMessage());
//        }
//    }
//
//    //쿠폰수정(관리자)
//    @Transactional
//    public ResponseDto updateCoupon(Long couponId, CouponUpdateRequestDto couponUpdateRequestDto) throws IOException {
//
//        Coupon editCoupon = mockCouponRepository.findById(couponId).orElseThrow(
//                () -> new CustomErrorException("해당 쿠폰을 찾을 수 없어 수정할 수 없습니다."));
//        //기존에 S3에 있는 사진 삭제하기기
//        if (couponUpdateRequestDto.getCouponImage() != null) {
//            //사진도 함께 업데이트 하는 경우: 기존사진 삭제후 업로드
//            deleteS3(editCoupon.getCouponImage());
//            String couponImage = s3Uploader.upload(couponUpdateRequestDto.getCouponImage(), "image");
//            if (couponImage == null) throw new CustomErrorException("이미지 업르드에 실패하였습니다");
//            editCoupon.updateCoupon(couponUpdateRequestDto, couponImage);
//        }else {
//            String couponImage = editCoupon.getCouponImage();
//            editCoupon.updateCoupon(couponUpdateRequestDto, couponImage);
//        }
//        return new ResponseDto("success", "수정성공");
//    }
//
//    //카테고리페이지 보여주기(디테일 제외)
//    public ResponseDto responseList1(String couponType, Long userId, int page, int size, String sortBy, boolean isAsc) {
//
//        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
//        Sort sort = Sort.by(direction, sortBy);
//        Pageable pageable = PageRequest.of(page, size, sort);
//
//        LocalDate now = LocalDate.now();
//        Page<Coupon> couponList = mockCouponRepository.findAllByCouponTypeAndCouponDespireAfter(couponType, now, pageable);
//        return showList(userId, couponList);
//    }
//
//
//    public ResponseDto showList(Long userId, Page<Coupon> couponList) {
//
//        List<CouponMainResponseDto> couponResponseDtoList = new ArrayList<>();
//        for (Coupon coupon : couponList) {
//            Folder folder = folderRepository.findByUserIdAndCouponId(userId, coupon.getId());
//            if (folder != null) {
//                Long couponSelect = 1L;
//                CouponMainResponseDto newCouponDto = new CouponMainResponseDto(coupon, couponSelect);
//                couponResponseDtoList.add(newCouponDto);
//            } else {
//                Long couponSelect = 0L;
//                CouponMainResponseDto newCouponDto = new CouponMainResponseDto(coupon, couponSelect);
//                couponResponseDtoList.add(newCouponDto);
//            }
//        }
//        return new ResponseDto("success", couponResponseDtoList);
//    }
//
//    //랭크보여주기 + 유저찜기록
//    public ResponseDto showRankList(Long userId, List<Coupon> couponList) {
//        List<CouponRankResponseDto> couponResponseDtoList = new ArrayList<>();
//        for (Coupon coupon : couponList) {
//            Folder folder = folderRepository.findByUserIdAndCouponId(userId, coupon.getId());
//            if (folder != null) {
//                Long couponSelect = 1L;
//                CouponRankResponseDto newCouponDto = new CouponRankResponseDto(coupon, couponSelect);
//                couponResponseDtoList.add(newCouponDto);
//            } else {
//                Long couponSelect = 0L;
//                CouponRankResponseDto newCouponDto = new CouponRankResponseDto(coupon, couponSelect);
//                couponResponseDtoList.add(newCouponDto);
//            }
//        }
//        return new ResponseDto("success", couponResponseDtoList);
//    }
//    //랭크만 보여주기
//    public ResponseDto responseRankList(List<Coupon> couponList) {
//        List<CouponRankResponseDto> couponResponseDtoList = new ArrayList<>();
//        for (Coupon coupon : couponList) {
//            CouponRankResponseDto newCouponDto = new CouponRankResponseDto(coupon);
//            couponResponseDtoList.add(newCouponDto);
//        }
//        return new ResponseDto("success", couponResponseDtoList);
//    }
//
//}
