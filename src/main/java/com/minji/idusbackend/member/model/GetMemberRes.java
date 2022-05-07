package com.minji.idusbackend.member.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetMemberRes {
    private String email;
    private String nickname;
    private String phoneNum;
    private String gender;
    private String birthday;
    private String notification;
}

//    private String email;
//    private String nickname;
//    private String password;
//    private Integer point;
//    private String phoneNum;
//    private String gender;
//    private String birthday;
//    private String notification;
