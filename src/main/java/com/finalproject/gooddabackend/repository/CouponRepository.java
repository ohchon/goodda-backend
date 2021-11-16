package com.finalproject.gooddabackend.repository;

import com.finalproject.gooddabackend.model.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Page<Coupon> findAllByCouponTypeAndCouponDespireAfter(String couponType, LocalDate now, Pageable pageable);
    List<Coupon> findTop10ByCouponDespireAfterOrderByCouponLikeDesc(LocalDate now);
    List<Coupon> findAllByOrderByIdDesc();

}
