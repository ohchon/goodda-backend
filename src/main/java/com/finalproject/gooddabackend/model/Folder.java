package com.finalproject.gooddabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "folder_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Column(nullable = false)
    private LocalDate couponDespire;

    public void addNewFolder(User user, Coupon coupon, LocalDate couponDespire) {
        this.user = user;
        this.coupon = coupon;
        this.couponDespire = couponDespire;
    }

    public void deleteFolder() {
        this.user.getFolderList().remove(this);
    }
}
