package com.minji.idusbackend.cbn.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
public class GetMyCbnList {
    private BigInteger member_idx;
    private BigInteger product_idx;
    private BigInteger cabinet_idx;
    private BigInteger idx;
    private String cabinetName;
}
