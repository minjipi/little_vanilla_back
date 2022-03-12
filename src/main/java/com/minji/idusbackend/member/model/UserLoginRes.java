package com.minji.idusbackend.member.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter

public class UserLoginRes extends User {
    int idx;
    String email;

    public UserLoginRes(int idx, String username, String password, Collection<?
                extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.idx = idx;
        this.email = username;
    }
}
