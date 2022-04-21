package com.minji.idusbackend.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetOrderList {
    private BigInteger idx;
    private int amount;
    private String status;
    private Timestamp ordered_at;
    private String name;
    private BigInteger brandIdx;
//    private String filename;
}
