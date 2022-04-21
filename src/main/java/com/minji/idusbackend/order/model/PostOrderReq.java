package com.minji.idusbackend.order.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostOrderReq {
    String status;
    List<ProductAmount> productAmountList = new ArrayList<>();
}
