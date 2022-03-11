package com.minji.idusbackend.product.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostProductReq {
    private String name;
    private int brandIdx;
    private int categoryIdx;
    private int price;
    private int salePrice;
    private String deliveryType;
    private String isTodayDeal;
}