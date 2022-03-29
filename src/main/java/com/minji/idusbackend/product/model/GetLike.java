package com.minji.idusbackend.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetLike {
    int member_idx;
    int product_idx;
}
