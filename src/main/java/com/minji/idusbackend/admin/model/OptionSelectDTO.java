package com.minji.idusbackend.admin.model;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OptionSelectDTO {
    private int idx;
    private String title;
    private int price;
}
