package com.finalproject.gooddabackend.repository;

import com.finalproject.gooddabackend.model.Folder;
import com.finalproject.gooddabackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    Folder findAllByUserIdAndCouponId(Long userId, Long CouponId);
    List<Folder> findAllByUserOrderByCouponDespireAsc(User user);
    List<Folder> deleteAllByCouponId(Long CouponId);
    List<Folder> deleteAllByUserId(Long UserId);
    Long countByCouponId(Long CouponId);
    Folder findByUserIdAndCouponId(Long userId, Long CouponId);

}
