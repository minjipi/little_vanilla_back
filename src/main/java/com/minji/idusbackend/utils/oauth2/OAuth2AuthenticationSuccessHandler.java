package com.minji.idusbackend.utils.oauth2;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.minji.idusbackend.config.JwtTokenUtil;
import com.minji.idusbackend.member.MemberDao;
import com.minji.idusbackend.member.MemberProvider;
import com.minji.idusbackend.member.MemberService;
import com.minji.idusbackend.member.model.UserLoginRes;
import com.minji.idusbackend.member.model.UserLoginResWithStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final MemberProvider memberProvider;

    @Autowired
    public OAuth2AuthenticationSuccessHandler(MemberProvider memberProvider){
        this.memberProvider = memberProvider;
    }

    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    MemberDao memberDao;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

//        login 성공한 사용자 목록.
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        Map<String, Object> kakao_account = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
        String email = (String) kakao_account.get("email");
        UserLoginResWithStatus userLoginResWithStatus = memberProvider.findByEmail(email);

        if(userLoginResWithStatus.getStatus()==2) {
            getRedirectStrategy().sendRedirect(request, response, UriComponentsBuilder.fromUriString("http://www.alittlevanilla.kro.kr/leavemember")
                    .build().toUriString());

        }

        String jwt = jwtTokenUtil.generateToken(userLoginResWithStatus.getUserLoginRes());

        String url = makeRedirectUrl(jwt);
        System.out.println("url: " + url);

        if (response.isCommitted()) {
            logger.debug("응답이 이미 커밋된 상태입니다. " + url + "로 리다이렉트하도록 바꿀 수 없습니다.");
            return;
        }
        getRedirectStrategy().sendRedirect(request, response, url);
    }


    private String makeRedirectUrl(String token) {
        return UriComponentsBuilder.fromUriString("http://www.alittlevanilla.kro.kr/oauth2/redirect/"+token)
                .build().toUriString();
    }
}