package com.minji.idusbackend.authLogin;


import com.minji.idusbackend.cbn.model.GetOneCbnRes;
import com.minji.idusbackend.member.MemberDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service

public class UserOAuth2Service extends DefaultOAuth2UserService {

    private final HttpSession httpSession;

    @Autowired
    MemberDao memberDao;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        log.info("attributes :: " + attributes);

//        가입한적 없음.
        if (memberDao.checkEmail((String) attributes.get("email")) == 0) {
            memberDao.createMemberKakao((String) attributes.get("email"));
        } else {
            System.out.println("가입한적 있음.");
        }

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_MEMBER")), attributes, "id");
    }


}