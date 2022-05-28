package com.minji.idusbackend.cart.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
public class PostCartRes {
    private BigInteger idx;
    private Integer status;
}
