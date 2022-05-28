package com.minji.idusbackend.cart.model;

import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class PostCartReq {
    private BigInteger productIdx;
    private BigInteger amount;

}
