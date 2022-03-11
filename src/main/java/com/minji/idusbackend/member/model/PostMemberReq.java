package com.minji.idusbackend.member.model;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostMemberReq {
    private String email;
    private String password;
    private String nickname;
//    private String name;
//    private int birthday;
//    private String gender;
}