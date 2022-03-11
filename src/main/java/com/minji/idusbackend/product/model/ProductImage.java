package com.minji.idusbackend.product.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class ProductImage {
    private int idx;

    @NonNull
    private String filename;

}


