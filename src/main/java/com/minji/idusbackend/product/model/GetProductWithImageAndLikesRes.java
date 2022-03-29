package com.minji.idusbackend.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetProductWithImageAndLikesRes {
    private int idx;
    private String name;
    private int brandIdx;
    private int categoryIdx;
    private int price;
    private int salePrice;
    private String deliveryType;
    private String isTodayDeal;
    private String filename;
    private int like_check;
}
