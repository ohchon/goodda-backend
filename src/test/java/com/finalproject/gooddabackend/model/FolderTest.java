package com.finalproject.gooddabackend.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FolderTest {
    @Test
    @DisplayName("정상 케이스")
    void createFolder_Normal(){
        // given
        LocalDate couponDespire = LocalDate.of(2021, 8, 9);
        User user = new User();
       Coupon coupon = new Coupon();

        Folder folder = new Folder();
        folder.addNewFolder(user, coupon, couponDespire);

        //then
        assertNull(folder.getId());
        assertEquals(user, folder.getUser());
        assertEquals(coupon, folder.getCoupon());
        assertEquals(couponDespire, folder.getCouponDespire());
    }
}