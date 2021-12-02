package com.finalproject.gooddabackend;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.finalproject.gooddabackend.model.Coupon;
import com.finalproject.gooddabackend.model.User;
import com.finalproject.gooddabackend.repository.CouponRepository;
import com.finalproject.gooddabackend.repository.FolderRepository;
import com.finalproject.gooddabackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Scheduler {
    private final UserRepository userRepository;
    private final FolderRepository folderRepository;
    private final CouponRepository couponRepository;
    private final AmazonS3Client amazonS3Client;
    private final String bucket = "good-da-bucket";


    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul") // cron에 따라 실행 (한국시간으로 매일 12시)
    @Transactional
    public void deleteUserBySchedule() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime finalDay = currentDateTime.minusDays(30);
        List<User> deleteUser = userRepository.findAllByStatusAndModifiedAtBefore(false, finalDay);
//        for (User user: deleteUser){
//           List<Folder> folderList = folderRepository.findAllByUserId(user.getId());
//            for(Folder folder = folderList){
//                Coupon coupon = couponRepository.findById(folder.getCoupon().getId()).orElseThrow(
//                        () -> new CustomErrorException("해당 정보가 존재하지 않습니다."));
//                Long couponLike = coupon.getCouponLike();
//                Long couponLikes = couponLike-1;
//                coupon.setCouponLike(couponLikes);
//            }
//        }
      for (User user: deleteUser){
          folderRepository.deleteAllByUserId(user.getId());
      }
        userRepository.deleteAllByStatusAndModifiedAtBefore(false, finalDay);
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    @Transactional
    public void deleteCouponBySchedule() {
         LocalDate now = LocalDate.now();
         LocalDate lastDay = now.minusDays(30);
         List<Coupon> deleteCoupon = couponRepository.findAllByCouponDespireBefore(lastDay);
        for (Coupon coupon: deleteCoupon){
            deleteS3(coupon.getCouponImage());
            folderRepository.deleteAllByCouponId(coupon.getId());
        }
        couponRepository.deleteAllByCouponDespireBefore(lastDay);
    }
    //S3 삭제
    public void deleteS3(@RequestParam String imageName) {
        //https://S3 버킷 URL/버킷에 생성한 폴더명/이미지이름
        String keyName = imageName.split("/")[4]; // 이미지이름만 추출

        try {amazonS3Client.deleteObject(bucket + "/image", keyName);
        } catch (AmazonServiceException e) {
            e.printStackTrace();
            throw new AmazonServiceException(e.getMessage());
        }
    }
    @Scheduled(cron = "0 0 0 1 * *", zone = "Asia/Seoul")
    @Transactional
    public void countCouponLikeBySchedule() {
        List<Coupon> allCoupons = couponRepository.findAll();
        for(Coupon coupon: allCoupons){
            Long couponLikes = folderRepository.countByCouponId(coupon.getId());
            coupon.setCouponLike(couponLikes);
        }
    }
}
