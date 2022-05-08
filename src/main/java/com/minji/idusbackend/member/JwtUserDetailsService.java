package com.minji.idusbackend.member;

import com.minji.idusbackend.member.model.UserLoginRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
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

        try {
            UserLoginRes userLoginRes = memberDao.findByEmail(username);
            System.out.println("== JwtUserDetailsService, loadUserByUsername, userLoginRes: ==" + userLoginRes);

            if (userLoginRes != null) {
                return userLoginRes;
            }

            else {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
        } catch (Exception exception) {
            System.out.println("본인 인증 실패");
            return (UserDetails) exception;
        }

    }
}
