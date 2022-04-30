package com.minji.idusbackend.member.model;

import lombok.*;

import java.math.BigInteger;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PatchMemberModityReq {
    private String nickname;
    private String email;
    private String phoneNum;
    private String gender;
    private String birthday;
    private String notification;
}
