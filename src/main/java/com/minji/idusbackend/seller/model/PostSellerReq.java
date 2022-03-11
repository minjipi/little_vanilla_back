package com.minji.idusbackend.seller.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostSellerReq {
    private String id;
    private String password;
    private String nickname;
    private String grade;
    private int birthday;
    private String gender;
}