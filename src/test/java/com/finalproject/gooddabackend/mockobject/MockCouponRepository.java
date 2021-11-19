//package com.finalproject.gooddabackend.mockobject;
//
//import com.finalproject.gooddabackend.model.Coupon;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//public class MockCouponRepository {
//
//    private List<Coupon> coupons = new ArrayList<>();
//
//    private Long couponId = 1L;
//
//    //쿠폰저장
//    public void save(Coupon coupon){
//        coupon.setId(couponId);
//        ++couponId;
//        coupons.add(coupon);
//    }
//    //쿠폰ID로 조회
//   public Optional<Coupon> findById(Long id) {
//        for (Coupon coupon : coupons){
//            if(coupon.getId().equals(id)){
//                return Optional.of(coupon);
//            }
//        }
//        return Optional.empty();
//   }
//
//   //쿠폰ID로 삭제
//    public void deleteById(Long id){
//        coupons.removeIf(coupon -> coupon.getId().equals(id));
////        for (Coupon coupon : coupons) {
////            if (coupon.getId().equals(id)) {
////                coupons.remove(coupon);
////            }
////        }
//    }
//    public Page<Coupon> findAllByCouponTypeAndCouponDespireAfter(String couponType, LocalDate now, Pageable pageable){
//
//        List<Coupon> couponList = new ArrayList<>();
//        for (Coupon coupon : coupons){
//            if (coupon.getCouponType().equals(couponType) && coupon.getCouponDespire().isAfter(now)) {
//               couponList.add(coupon);
//            }
//        }
//        int start = (int)pageable.getOffset();
//        int end = Math.min((start + pageable.getPageSize()), couponList.size());
//        return new PageImpl<>(couponList.subList(start, end), pageable, couponList.size());
//    }
//}
