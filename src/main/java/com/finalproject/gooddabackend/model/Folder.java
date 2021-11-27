package com.finalproject.gooddabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    public Folder(User user, Coupon coupon){
        this.user = user;
        this.coupon = coupon;
    }
    public void addNewFolder(User user, Coupon coupon) {
        this.user = user;
        this.coupon = coupon;
    }

    public void deleteFolder() {
        this.user.getFolderList().remove(this);
    }
}
