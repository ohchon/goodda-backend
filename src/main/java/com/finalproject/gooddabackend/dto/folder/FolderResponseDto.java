package com.finalproject.gooddabackend.dto.folder;

import com.finalproject.gooddabackend.dto.coupon.CouponResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FolderResponseDto {
    private List<CouponResponseDto> coupons;

    public FolderResponseDto(List<CouponResponseDto>responseDtoList) {

        this.coupons = responseDtoList;
    }
}
