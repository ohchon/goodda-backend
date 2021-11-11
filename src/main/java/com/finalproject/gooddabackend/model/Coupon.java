package com.finalproject.gooddabackend.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class Coupon {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String couponBrand;

    @Column(nullable = false)
    private String couponTitle;

    @Column(nullable = false)
    private String couponSubTitle;

    @Column(nullable = false)
    private String couponImage;

    @Column(nullable = false)
    private String couponLogo;

    @Column(nullable = false)
    private String couponType;

    @Column(nullable = false)
    private String couponDesc;

    @Column(nullable = false)
    private String couponUrl;

    @Column(nullable = false)
    private LocalDate couponCreate;

    @Column(nullable = false)
    private LocalDate couponDespire;

    @Column(nullable = false)
    private Long couponLike;


        public Coupon(String couponBrand, String couponTitle, String couponSubTitle, String couponImage, String couponLogo,  String couponType, String couponDesc,  String couponUrl, LocalDate couponCreate, LocalDate couponDespire, Long couponLike) {

        this.couponBrand = couponBrand;
        this.couponTitle = couponTitle;
        this.couponSubTitle= couponSubTitle;
        this.couponImage = couponImage;
        this.couponLogo = couponLogo;
        this.couponType = couponType;
        this.couponDesc = couponDesc;
        this.couponUrl = couponUrl;
        this.couponCreate = couponCreate;
        this.couponDespire = couponDespire;
        this.couponLike = couponLike;
    }

    public void updateCoupon(String couponBrand, String couponTitle, String couponSubTitle, String couponImage, String couponLogo,  String couponType, String couponDesc,  String couponUrl, LocalDate couponCreate, LocalDate couponDespire){
        this.couponBrand = couponBrand;
        this.couponTitle = couponTitle;
        this.couponSubTitle = couponSubTitle;
        this.couponImage = couponImage;
        this.couponLogo = couponLogo;
        this.couponType = couponType;
        this.couponDesc = couponDesc;
        this.couponUrl = couponUrl;
        this.couponCreate = couponCreate;
        this.couponDespire = couponDespire;
    }
}