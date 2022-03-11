package com.minji.idusbackend.admin.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class OptionDTO {
    private int idx;
    private String title;

    @Builder.Default
    List<OptionSelectDTO> optionSelectDTOList = new ArrayList<>();
}
