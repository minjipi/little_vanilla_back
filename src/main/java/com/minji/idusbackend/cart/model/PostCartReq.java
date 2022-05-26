package com.minji.idusbackend.cart.model;

import com.minji.idusbackend.order.model.ProductAmount;
import lombok.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class PostCartReq {
    List<ProductAmount> productAmountList = new ArrayList<>();
}
