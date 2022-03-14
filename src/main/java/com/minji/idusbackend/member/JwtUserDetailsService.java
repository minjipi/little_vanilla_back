package com.minji.idusbackend.member;

import com.minji.idusbackend.member.model.UserLoginRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    MemberDao memberDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserLoginRes userLoginRes = memberDao.findByEmailAndName(username);
        System.out.println("userLoginRes: " + userLoginRes);


        if (userLoginRes != null) {
            return userLoginRes;
        }

        else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
