package com.minji.idusbackend.pay.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class PostOrderInfo {
    private String impUid;
    private String pg;
    private String pay_method;
    private String merchant_uid;
    private String name;
    private Integer amount;
    private String buyer_name;
    private String buyer_tel;
    private String buyer_email;
    private BigInteger idx;
}
