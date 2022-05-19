package com.minji.idusbackend.pay.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostOrderResponse {
    private Integer idx;
    private Integer status;
}