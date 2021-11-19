package com.finalproject.gooddabackend.repository;

import com.finalproject.gooddabackend.model.Folder;
import com.finalproject.gooddabackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    Folder findAllByUserIdAndCouponId(Long userId, Long CouponId);
    List<Folder> findAllByUserOrderByCouponDespireAsc(User user);
    void deleteAllByCouponId(Long CouponId);
    void deleteAllByUserId(Long UserId);
    Long countByCouponId(Long CouponId);
    Folder findByUserIdAndCouponId(Long userId, Long CouponId);

}
