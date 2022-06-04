package com.minji.idusbackend.member.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.math.BigInteger;
import java.util.Collection;

@Getter
@Setter

public class UserLoginResWithStatus {
    UserLoginRes userLoginRes;
    Integer status;

    public UserLoginResWithStatus(BigInteger idx, String username, String password, String nickname, Collection<?
            extends GrantedAuthority> authorities, Integer status) {
        this.userLoginRes = new UserLoginRes(idx, username, password, nickname, authorities);
        this.status = status;
    }
}
