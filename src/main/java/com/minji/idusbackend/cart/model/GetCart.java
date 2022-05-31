package com.minji.idusbackend.cart.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetCart {
    private BigInteger idx;
    private BigInteger productIdx;
    private BigInteger brandIdx;
    private int amount;
    private String name;
    private BigInteger price;
    private BigInteger salePrice;
    private String deliveryType;
    private String isTodayDeal;
    private String filename;
}
