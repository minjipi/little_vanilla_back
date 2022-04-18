//package com.minji.idusbackend.authLogin;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class KakaoAuthService {
//    private final ClientKakao clientKakao; // private final ClientGoogle clientGoogle;
//    private final MemberQuerydslRepository memberQuerydslRepository;
//    private final AuthTokenProvider authTokenProvider;
//    private final MemberRepository memberRepository;
//
//    @Transactional
//    public AuthResponse login(AuthRequest authRequest) {
//        Members kakaoMember = clientKakao.getUserData(authRequest.getAccessToken()); // userData 담기
//        // Members googleMember = clientGoogle.getUserData(authRequest.getAccessToken());
//        String socialId = kakaoMember.getSocialId();
//        // String socialId = googleMember.getSocialId();
//        Members member = memberQuerydslRepository.findBySocialId(socialId);
//
//        AuthToken appToken = authTokenProvider.createUserAppToken(socialId); // 신규 토큰 생성
//
//        if (member == null) {
//            memberRepository.save(kakaoMember);
//            // memberRepository.save(googleMember);
//        }
//
//        return AuthResponse.builder() // /auth/kakao와 /auth/google의 응답의 body로 AccessToken(AppToken)을 보내주기위해 builder 사용
//                .appToken(appToken.getToken())
//                .build();
//    }
//}
