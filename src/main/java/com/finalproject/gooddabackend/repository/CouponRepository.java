package com.finalproject.gooddabackend.repository;

import com.finalproject.gooddabackend.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    List<Coupon> findAllByCouponTypeAndCouponDespireAfterOrderByCouponDespireAsc(String couponType, LocalDate now);
    List<Coupon> findAllByCouponDespireAfterOrderByCouponLikeDesc(LocalDate now);

}
