//package com.minji.idusbackend.authLogin;
//
//import io.jsonwebtoken.*;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//import java.security.Key;
//import java.util.Date;
//
//@Slf4j
//@RequiredArgsConstructor
//public class AuthToken {
//    @Getter
//    private final String token;
//    private final Key key;
//
//    private static final String AUTHORITIES_KEY = "role";
//
//    AuthToken(String socialId, RoleType roleType, Date expiry, Key key) {
//        String role = roleType.toString(); // USER, ADMIN
//        this.key = key;
//        this.token = createAuthToken(socialId, role, expiry);
//    }
//
//    private String createAuthToken(String socialId, String role, Date expiry) { // AccessToken(AppToken) 생성
//        return Jwts.builder()
//                .setSubject(socialId)
//                .claim(AUTHORITIES_KEY, role)
//                .signWith(key, SignatureAlgorithm.HS256)
//                .setExpiration(expiry)
//                .compact();
//    }
//
//    public boolean validate() { // AccessToken(AppToken) 유효한지 체크
//        return this.getTokenClaims() != null;
//    }
//
//    public Claims getTokenClaims() {
//        try {
//            return Jwts.parserBuilder()
//                    .setSigningKey(key)
//                    .build()
//                    .parseClaimsJws(token)
//                    .getBody(); // token의 Body가 하기 exception들로 인해 유효하지 않으면 각각에 해당하는 로그 콘솔에 찍음
//        } catch (SecurityException e) {
//            log.info("Invalid JWT signature.");
//        } catch (MalformedJwtException e) {
//            log.info("Invalid JWT token.");
//            // 처음 로그인(/auth/kakao, /auth/google) 시, AccessToken(AppToken) 없이 접근해도 token validate을 체크하기 때문에 exception 터트리지 않고 catch합니다.
//        } catch (ExpiredJwtException e) {
//            log.info("Expired JWT token.");
//        } catch (UnsupportedJwtException e) {
//            log.info("Unsupported JWT token.");
//        } catch (IllegalArgumentException e) {
//            log.info("JWT token compact of handler are invalid.");
//        }
//        return null;
//    }
//
//}
