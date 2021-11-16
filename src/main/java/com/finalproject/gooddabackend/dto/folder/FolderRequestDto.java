package com.finalproject.gooddabackend.dto.folder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class  FolderRequestDto {

    @NotNull
    private Long couponId;

}