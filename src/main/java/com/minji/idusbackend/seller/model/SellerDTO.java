package com.minji.idusbackend.seller.model;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter

public class SellerDTO {
    private String id;
    private String password;
    private String nickname;
    private String grade;
}


