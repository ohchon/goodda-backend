package com.finalproject.gooddabackend.service;

import com.finalproject.gooddabackend.dto.coupon.CouponResponseDto;
import com.finalproject.gooddabackend.dto.folder.FolderResponseDto;
import com.finalproject.gooddabackend.exception.CustomErrorException;
import com.finalproject.gooddabackend.model.Coupon;
import com.finalproject.gooddabackend.model.Folder;
import com.finalproject.gooddabackend.model.User;
import com.finalproject.gooddabackend.repository.CouponRepository;
import com.finalproject.gooddabackend.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FolderService {

    private final FolderRepository folderRepository;
    private final CouponRepository couponRepository;


    public Coupon loadCoupon(Long couponId) {
        return couponRepository.findById(couponId).orElseThrow(
                () -> new CustomErrorException("해당 정보가 존재하지 않습니다.")
        );
    }

    // 보관함 내용 삭제
    @Transactional
    public void deleteFolder(User user, Long couponId) {
        Coupon coupon = loadCoupon(couponId);
        Folder folder = folderRepository.findAllByUserIdAndCouponId(user.getId(), couponId);
        folder.deleteFolder();
        folderRepository.delete(folder);
        //쿠폰찜숫자 바꾸기
        Long couponLike = coupon.getCouponLike();
        Long couponLikes = couponLike-1;
        coupon.setCouponLike(couponLikes);
    }



    //보관함 조희
    public FolderResponseDto getCoupon(User user) {
       List<Folder> folderList = folderRepository.findAllByUser(user);

       List<CouponResponseDto> couponList = new ArrayList<>();
        for(Folder folder: folderList){
            Coupon coupon = folder.getCoupon();
            CouponResponseDto couponResponseDto = new CouponResponseDto(coupon);
            couponList.add(couponResponseDto);
        }
        Collections.sort(couponList);
        return new FolderResponseDto(couponList);
    }
}
