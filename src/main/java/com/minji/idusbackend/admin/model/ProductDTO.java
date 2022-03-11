package com.minji.idusbackend.admin.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProductDTO {
    private int idx;
    private String productName;
    private int price;
    private int salePrice;
    private int quantity;
    private String productInfo;
    private String tags;

    @Builder.Default
    private List<ProductImageUploadDTO> productImageUploadDTOList = new ArrayList<>();

    @Builder.Default
    private List<OptionDTO> optionDTOList = new ArrayList<>();
}
