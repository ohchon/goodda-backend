package com.finalproject.gooddabackend.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.finalproject.gooddabackend.awsS3.S3Uploader;
import com.finalproject.gooddabackend.dto.ResponseDto;
import com.finalproject.gooddabackend.dto.coupon.CouponCreateRequestDto;
import com.finalproject.gooddabackend.dto.coupon.CouponMainResponseDto;
import com.finalproject.gooddabackend.dto.coupon.CouponRankResponseDto;
import com.finalproject.gooddabackend.dto.coupon.CouponUpdateRequestDto;
import com.finalproject.gooddabackend.exception.CustomErrorException;
import com.finalproject.gooddabackend.model.Coupon;
import com.finalproject.gooddabackend.model.Folder;
import com.finalproject.gooddabackend.repository.CouponRepository;
import com.finalproject.gooddabackend.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final FolderRepository folderRepository;
    private final S3Uploader s3Uploader;
    private final AmazonS3Client amazonS3Client;
    private final String bucket = "good-da-bucket";


    //카테고리페이지 보여주기(디테일 제외)
    public ResponseDto responseList1(String couponType, Long userId, int page, int size, String sortBy, boolean isAsc) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        LocalDate now = LocalDate.now();
        Page<Coupon> couponList = couponRepository.findAllByCouponTypeAndCouponDespireAfter(couponType, now, pageable);
        return showList(userId, couponList);
    }

    //랭크페이지 보여주기(디테일 제외)
    public ResponseDto responseRankList(Page<Coupon> couponList) {
        List<CouponRankResponseDto> couponResponseDtoList = new ArrayList<>();
        for (Coupon coupon : couponList) {
            CouponRankResponseDto newCouponDto = new CouponRankResponseDto(
                    coupon.getId(),
                    coupon.getCouponBrand(),
                    coupon.getCouponSubTitle(),
                    coupon.getCouponLogo(),
                    coupon.getCouponCreate(),
                    coupon.getCouponDespire(),
                    coupon.getCouponLike()
            );
            couponResponseDtoList.add(newCouponDto);
        }
        return new ResponseDto("success", couponResponseDtoList);
    }



    //쿠폰생성(관리자)
    public ResponseDto createCoupon(CouponCreateRequestDto couponCreateRequestDto) throws IOException {
        String couponBrand = couponCreateRequestDto.getCouponBrand();
        String couponTitle = couponCreateRequestDto.getCouponTitle();
        String couponSubTitle = couponCreateRequestDto.getCouponSubTitle();
        String couponLogo = couponCreateRequestDto.getCouponLogo();
        String couponType = couponCreateRequestDto.getCouponType();
        String couponDesc = couponCreateRequestDto.getCouponDesc();
        String couponUrl = couponCreateRequestDto.getCouponUrl();
        LocalDate couponCreate = couponCreateRequestDto.getCouponCreate();
        LocalDate couponDespire = couponCreateRequestDto.getCouponDespire();
        Long couponLike = 0L;
        if(couponCreateRequestDto.getCouponImage()!=null) {

            String couponImage = s3Uploader.upload(couponCreateRequestDto.getCouponImage(), "image");

            Coupon coupon = new Coupon(couponBrand, couponTitle, couponSubTitle, couponImage, couponLogo, couponType, couponDesc, couponUrl, couponCreate, couponDespire, couponLike);

            couponRepository.save(coupon);

            return new ResponseDto("success","쿠폰생성 성공");
        }else{
            String couponImage = "";

            Coupon coupon = new Coupon(couponBrand, couponTitle, couponSubTitle, couponImage, couponLogo, couponType, couponDesc, couponUrl, couponCreate, couponDespire, couponLike);

            couponRepository.save(coupon);

            return new ResponseDto("success","쿠폰생성 성공");
        }
    }

    //쿠폰삭제(관리자)
    @Transactional
    public ResponseDto deleteCoupon(Long couponId){
        folderRepository.deleteAllByCouponId(couponId);
        couponRepository.deleteById(couponId);
        //원래는 아이디로 데이터 찾아서 거기 이미지 S3주소에 있는 이미지까지 삭제
        return new ResponseDto("success","쿠폰삭제 성공");
    }

    //쿠폰수정(관리자)
    @Transactional
    public ResponseDto updateCoupon(Long couponId, CouponUpdateRequestDto couponUpdateRequestDto) throws IOException {

        Coupon editCoupon = couponRepository.findById(couponId).orElseThrow(
                () -> new CustomErrorException("해당 쿠폰을 찾을 수 없어 수정할 수 없습니다."));

        String couponBrand = couponUpdateRequestDto.getCouponBrand();
        String couponTitle = couponUpdateRequestDto.getCouponTitle();
        String couponSubTitle = couponUpdateRequestDto.getCouponSubTitle();
        String couponImage = editCoupon.getCouponImage(); // 안했을떄를 대비
        String couponLogo = couponUpdateRequestDto.getCouponLogo();
        String couponType = couponUpdateRequestDto.getCouponType();
        String couponDesc = couponUpdateRequestDto.getCouponDesc();
        String couponUrl = couponUpdateRequestDto.getCouponUrl();
        LocalDate couponCreate = couponUpdateRequestDto.getCouponCreate();
        LocalDate couponDespire = couponUpdateRequestDto.getCouponDespire();
        //기존에 S3에 있는 사진 삭제하기기
//        if (couponUpdateRequestDto.getCouponImage() != null) {
//            //사진도 함께 업데이트 하는 경우: 기존사진 삭제후 업로드
////            Coupon foundCoupon = couponRepository.findById(couponId).orElseThrow(
////                    () -> new CustomErrorException("해당 쿠폰을 찾을 수 없어 수정할 수 없습니다."));
//            //deleteS3(foundCoupon.getCouponImage()); //지우고 넣고 싶지만 안돼서 S3에 있는것은 냅두고 그 위에 올리기
//            String couponImage = s3Uploader.upload(couponUpdateRequestDto.getCouponImage(), "image");
//            editCoupon.updateCoupon(couponBrand, couponTitle, couponSubTitle, couponImage, couponLogo, couponType, couponDesc, couponUrl, couponCreate, couponDespire);
//            return new ResponseDto("success", "수정성공");
//        } else {
//            String couponImage = editCoupon.getCouponImage();
//            editCoupon.updateCoupon(couponBrand, couponTitle, couponSubTitle, couponImage, couponLogo, couponType, couponDesc, couponUrl, couponCreate, couponDespire);
//
//            return new ResponseDto("success", "수정성공");
//        }
        //기존에 S3에 있는 사진 삭제하기기
        if(couponUpdateRequestDto.getCouponImage() instanceof MultipartFile){
            //사진도 함께 업데이트 하는 경우: 기존사진 삭제후 업로드
//            Coupon foundCoupon = couponRepository.findById(couponId).orElseThrow(
//                    () -> new CustomErrorException("해당 쿠폰을 찾을 수 없어 수정할 수 없습니다."));
            //deleteS3(foundCoupon.getCouponImage()); //지우고 넣고 싶지만 안돼서 S3에 있는것은 냅두고 그 위에 올리기
            couponImage = s3Uploader.upload(couponUpdateRequestDto.getCouponImage(), "image");
            if(couponImage == null) throw new CustomErrorException("이미지 업르드에 실패하였습니다");
        }
        editCoupon.updateCoupon(couponBrand, couponTitle, couponSubTitle, couponImage, couponLogo, couponType, couponDesc, couponUrl, couponCreate, couponDespire);

        return new ResponseDto("success", "수정성공");

    }

    public ResponseDto showList(Long userId, Page<Coupon> couponList){

        List<CouponMainResponseDto> couponResponseDtoList = new ArrayList<>();
        for (Coupon coupon : couponList) {
            Folder folder = folderRepository.findByUserIdAndCouponId(userId, coupon.getId());
            if (folder != null){
                CouponMainResponseDto newCouponDto = new CouponMainResponseDto(
                        coupon.getId(),
                        coupon.getCouponBrand(),
                        coupon.getCouponSubTitle(),
                        coupon.getCouponLogo(),
                        coupon.getCouponCreate(),
                        coupon.getCouponDespire(),
                        coupon.getCouponLike(),
                        1L
                );
                couponResponseDtoList.add(newCouponDto);
            } else {
                CouponMainResponseDto newCouponDto = new CouponMainResponseDto(
                        coupon.getId(),
                        coupon.getCouponBrand(),
                        coupon.getCouponSubTitle(),
                        coupon.getCouponLogo(),
                        coupon.getCouponCreate(),
                        coupon.getCouponDespire(),
                        coupon.getCouponLike(),
                        0L
                );
                couponResponseDtoList.add(newCouponDto);
            }
        }
        return new ResponseDto("success", couponResponseDtoList);
    }

}

    //삭제 기능인데 잘 안된다...
//    public void deleteS3(@RequestParam String imageName){
//        //https://S3 버킷 URL/버킷에 생성한 폴더명/이미지이름
//        String keyName = imageName.split("/")[4]; // 이미지이름만 추출
//
//        try {amazonS3Client.deleteObject(bucket + "/image", keyName);
//        }catch (AmazonServiceException e){
//            e.printStackTrace();
//            throw new AmazonServiceException(e.getMessage());
//        }
//    }


