package com.minji.idusbackend.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetOrder {
    private BigInteger idx;
    private BigInteger productIdx;
    private BigInteger brandIdx;
    private String name;
    private BigInteger price;
    private BigInteger salePrice;
    private String deliveryType;
    private String isTodayDeal;
    private String filename;
    private Timestamp create_timestamp;
}
