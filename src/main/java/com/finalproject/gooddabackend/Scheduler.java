package com.finalproject.gooddabackend;

import com.finalproject.gooddabackend.model.Coupon;
import com.finalproject.gooddabackend.model.User;
import com.finalproject.gooddabackend.repository.CouponRepository;
import com.finalproject.gooddabackend.repository.FolderRepository;
import com.finalproject.gooddabackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul") // cron에 따라 실행 (한국시간으로 매일 12시)
    @Transactional
    public void deleteUserBySchedule() {
        System.out.println("스케쥴시작");
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime finalDay = currentDateTime.minusDays(30);
        List<User> deleteUser = userRepository.findAllByStatusAndModifiedAtBefore(false, finalDay);
        System.out.println("폴더삭제");
      for (User user: deleteUser){
          folderRepository.deleteAllByUserId(user.getId());
      }
        System.out.println("유저삭제");
        userRepository.deleteAllByStatusAndModifiedAtBefore(false, finalDay);
        System.out.println("유저삭제끝");
        System.out.println("스케쥴끝");
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    @Transactional
    public void deleteCouponBySchedule() {
        System.out.println("스케쥴시작");
         LocalDate now = LocalDate.now();
         LocalDate lastDay = now.minusDays(30);
         List<Coupon> deleteCoupon = couponRepository.findAllByCouponDespireBefore(lastDay);
        System.out.println("폴더삭제");
        for (Coupon coupon: deleteCoupon){
            folderRepository.deleteAllByCouponId(coupon.getId());
        }
        System.out.println("쿠폰삭제");
        couponRepository.deleteAllByCouponDespireBefore(lastDay);
        System.out.println("쿠폰삭제끝");
        System.out.println("스케쥴끝");
    }
}
