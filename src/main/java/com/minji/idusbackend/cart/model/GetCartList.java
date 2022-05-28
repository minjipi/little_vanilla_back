package com.minji.idusbackend.cart.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetCartList {
    private BigInteger idx;
    private BigInteger brandIdx;
    private int amount;
    private String name;
    private BigInteger price;
    private BigInteger salePrice;
    private String deliveryType;
    private String isTodayDeal;
    private String filename;
}
