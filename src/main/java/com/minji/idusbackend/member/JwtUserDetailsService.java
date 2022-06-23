package com.minji.idusbackend.member;

import com.minji.idusbackend.config.BaseException;
import com.minji.idusbackend.member.model.UserLoginRes;
import com.minji.idusbackend.member.model.UserLoginResWithStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

import static com.minji.idusbackend.config.BaseResponseStatus.RESPONSE_NULL_ERROR_BY_IDX;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    MemberDao memberDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserLoginResWithStatus userLoginResWithStatus = memberDao.findByEmailWithStatus(username);

            if (userLoginResWithStatus.getUserLoginRes() != null) {
                if(userLoginResWithStatus.getStatus()==0) {
                    // 인증을 아직 안받은애
                    System.out.println("인증이 필요합니다.");

                    throw new UsernameNotFoundException("Email cert please. Id: " + username);

                } else if (userLoginResWithStatus.getStatus()==1) {
                    // 인증을 받은 애
                    return userLoginResWithStatus.getUserLoginRes();
                } else if (userLoginResWithStatus.getStatus() == 2) {
                    // 탈퇴했던 애
                    System.out.println("탈퇴한 회원입니다.");

                    throw new UsernameNotFoundException("User not found with username: " + username);
                }
                return userLoginResWithStatus.getUserLoginRes();
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
