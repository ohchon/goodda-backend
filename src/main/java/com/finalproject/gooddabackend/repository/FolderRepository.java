package com.finalproject.gooddabackend.repository;

import com.finalproject.gooddabackend.model.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    Folder findAllByUserIdAndCouponId(Long userId, Long couponId);
    void deleteAllByCouponId(Long CouponId);
    void deleteAllByUserId(Long UserId);
    Folder findByUserIdAndCouponId(Long userId, Long CouponId);
    List<Folder> findAllByUserId(Long userId);
    Long countByCouponId(Long CouponId);
//    List<Folder> findAllByUserId(Long userid);
}
