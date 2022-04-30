package com.minji.idusbackend.member.model;

import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PatchPhoneModifyReq {
    private BigInteger idx;
    private String phoneNum;
}


