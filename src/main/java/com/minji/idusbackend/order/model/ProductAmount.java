package com.minji.idusbackend.order.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductAmount {
    int product_idx;
    int amount;
}
