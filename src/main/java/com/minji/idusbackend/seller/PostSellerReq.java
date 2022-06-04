package com.minji.idusbackend.seller;

import com.minji.idusbackend.member.model.Authority;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostSellerReq {
    private String email;
    private String password;
    private String nickname;
    private String phoneNum;
    private String gender;
    private String birthday;
    private String notification;
}


