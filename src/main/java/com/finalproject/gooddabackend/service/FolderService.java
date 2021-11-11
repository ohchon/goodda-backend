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
import java.util.List;
import java.util.stream.Collectors;

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
        Folder folder = folderRepository.findAllByUserIdAndCouponId(user.getId(), couponId);
        folder.deleteFolder();
        folderRepository.delete(folder);

        //쿠폰찜숫자 바꾸기
        Coupon editCoupon = couponRepository.findById(couponId).orElseThrow(
                () -> new CustomErrorException("해당 쿠폰을 찾을 수 없어 수정할 수 없습니다."));
        Long couponLikes = folderRepository.countByCouponId(couponId);
        editCoupon.setCouponLike(couponLikes);
    }

    //보관함 조희
    public FolderResponseDto getCoupon(User user) {
        List<CouponResponseDto> responseDtoList = folderRepository.findAllByUserOrderByCouponDespireAsc(user).stream().map(CouponResponseDto::new).collect(Collectors.toCollection(ArrayList::new));
        return new FolderResponseDto(responseDtoList);
    }
}
