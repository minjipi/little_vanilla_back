package com.minji.idusbackend.cbn.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
public class GetOneCbnRes {
    private BigInteger product_idx;
    private BigInteger cabinet_idx;
    private BigInteger idx;
    private String name;
    private BigInteger brandIdx;
    private BigInteger categoryIdx;
    private BigInteger price;
    private BigInteger salePrice;
    private String deliveryType;
    private String isTodayDeal;
    private String filename;

}
