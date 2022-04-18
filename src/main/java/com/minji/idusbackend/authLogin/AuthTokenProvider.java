package com.minji.idusbackend.authLogin;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;

@Component
public class AuthTokenProvider {

//    @Value("${app.auth.tokenExpiry}")
//    private String expiry; // 토큰 만료일
//
//    private final Key key;
//    private static final String AUTHORITIES_KEY = "role"; // getAuthentication에서 사용자 권한 체크 위함
//
//    public AuthTokenProvider(@Value("${app.auth.tokenSecret}") String secretKey) { // 생성자
//        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
//    }
//
//    public AuthToken createToken(String id, RoleType roleType, String expiry) { // 추후 roleType 추가 시 interface 역할 하기 위해 생성
//        Date expiryDate = getExpiryDate(expiry);
//        return new AuthToken(id, roleType, expiryDate, key);
//    }
//
//    public AuthToken createUserAppToken(String id) { // USER에 대한 AccessToken(AppToken) 생성
//        return createToken(id, RoleType.USER, expiry);
//    }
//
//    public AuthToken convertAuthToken(String token) { // String to AuthToken
//        return new AuthToken(token, key);
//    }
//
//    public static Date getExpiryDate(String expiry) { // String to Date
//        return new Date(System.currentTimeMillis() + Long.parseLong(expiry));
//    }
//
//    public Authentication getAuthentication(AuthToken authToken) {
//
//        if(authToken.validate()) {
//
//            Claims claims = authToken.getTokenClaims();
//            Collection<? extends GrantedAuthority> authorities =
//                    Arrays.stream(new String[]{claims.get(AUTHORITIES_KEY).toString()})
//                            .map(SimpleGrantedAuthority::new)
//                            .collect(Collectors.toList());
//
//            User principal = new User(claims.getSubject(), "", authorities);
//            // 사실상 principal에 저장되는 값은 socialId값과 role뿐(소셜 로그인만 사용하여 password 저장하지 않아 ""로 넣음)
//            return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);
//        } else {
//            throw new TokenValidFailedException();
//        }
//    }
}
