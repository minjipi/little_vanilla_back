package com.minji.idusbackend.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
public class GetProductWithImageAndLikesRes {
    private BigInteger idx;
    private String name;
    private BigInteger brandIdx;
    private BigInteger categoryIdx;
    private BigInteger price;
    private BigInteger salePrice;
    private String deliveryType;
    private String isTodayDeal;
    private String filename;
    private Boolean like_check;
}
