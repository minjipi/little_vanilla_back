package com.minji.idusbackend.member;

import com.minji.idusbackend.config.BaseException;
import com.minji.idusbackend.config.BaseResponse;
import com.minji.idusbackend.config.BaseResponseStatus;
import com.minji.idusbackend.config.JwtTokenUtil;
import com.minji.idusbackend.member.model.*;
import com.minji.idusbackend.seller.PostSellerReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.math.BigInteger;
import java.util.UUID;

import static com.minji.idusbackend.config.BaseResponseStatus.*;
import static com.minji.idusbackend.utils.Validation.isValidatedIdx;

@CrossOrigin("http://www.alittlevanilla.kro.kr")
@RestController
@RequestMapping("/member")
public class MemberController {
    private final MemberDao memberDao;

    @Autowired
    MemberService memberService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private EmailCertService emailCertService;

    @Autowired
    public MemberController(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @ResponseBody
    @PostMapping("/signup")
    public BaseResponse<PostMemberRes> createMember(@RequestBody PostMemberReq postMemberReq) {

        try {
            if (postMemberReq.getNickname() == null) {
                return new BaseResponse<>(POST_USER_NICKNAME_NULL);
            }

            String token = UUID.randomUUID().toString();

            System.out.println("====== Controller : createMember : Req ====== " + postMemberReq);

            PostMemberRes postMemberRes = memberService.createMember(postMemberReq, token);
            UserDetails userDetails = memberService.findByEmailStatusZero(postMemberReq.getEmail());

            final String jwt = jwtTokenUtil.generateToken(userDetails);

            emailCertService.createEmailConfirmationToken(token, postMemberReq.getEmail(), jwt);

            return new BaseResponse<>(postMemberRes);

        } catch (Exception exception) {
            System.out.println(exception);
            return new BaseResponse<>(BaseResponseStatus.FAIL);
        }
    }

    @ResponseBody
    @GetMapping("/confirm")
    public RedirectView signupConfirm(GetEmailConfirmReq getEmailConfirmReq) {
        GetEmailCertRes getEmailCertRes = emailCertService.signupConfirm(getEmailConfirmReq);

        if (getEmailCertRes.getStatus() == 1) {
            return new RedirectView("http://www.alittlevanilla.kro.kr/emailconfirm/" + getEmailConfirmReq.getJwt());
        } else {
//             수정 요청 : 인증 에러 페이지.
            return new RedirectView("http://www.alittlevanilla.kro.kr");
        }
    }

    @ResponseBody
    @GetMapping("/modify")
    public BaseResponse<GetMemberRes> getModifyMemberInfo(@AuthenticationPrincipal UserLoginRes userLoginRes) {

        if (userLoginRes == null) {
            return new BaseResponse<>(NOT_LOGIN);
        }
        try {
            BigInteger userIdx = userLoginRes.getIdx();
            GetMemberRes getMemberRes = memberService.getModifyMemberInfo(userIdx);
            return new BaseResponse<>(getMemberRes);

        } catch (Exception exception) {
            return new BaseResponse<>(EMPTY_IDX);
        }
    }


    @ResponseBody
    @PatchMapping("/modify/{idx}")
    public BaseResponse<String> modifyMemberInfo(@AuthenticationPrincipal UserLoginRes userLoginRes, @PathVariable("idx") BigInteger idx, @RequestBody PatchMemberModityReq patchMemberModityReq) {

        if (idx == null) {
            return new BaseResponse<>(EMPTY_IDX);
        }
        if (!isValidatedIdx(idx)) {
            return new BaseResponse<>(INVALID_IDX);
        }
        if (userLoginRes == null) {
            return new BaseResponse<>(NOT_LOGIN);
        }

        try {
            BigInteger userIdx = userLoginRes.getIdx();

            if (!userIdx.equals(idx)) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            memberService.modifyMemberInfo(patchMemberModityReq, idx);
            String result = patchMemberModityReq.getNickname() + "로 변경 완료.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @PostMapping("/sellersignup")
    public BaseResponse<PostMemberRes> createSeller(@RequestBody PostSellerReq postSellerReq) {

        try {
            System.out.println("createSeller Req: " + postSellerReq);

            PostMemberRes postMemberRes = memberService.createSeller(postSellerReq);
            return new BaseResponse<>(postMemberRes);

        } catch (Exception exception) {
            System.out.println(exception);
            return new BaseResponse<>(BaseResponseStatus.FAIL);
        }
    }

    //     계정 탈퇴 API
    @ResponseBody
    @PatchMapping("/delete/{idx}")
    public BaseResponse<GetMemberRes> deleteUser(@PathVariable BigInteger idx) {
        if (idx == null) {
            return new BaseResponse<>(EMPTY_IDX);
        }
        if (!isValidatedIdx(idx)) {
            return new BaseResponse<>(INVALID_IDX);
        }

        try {
            GetMemberRes getMemberRes = memberService.deleteUser(idx);
            return new BaseResponse<>(getMemberRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("{email}")
    public Boolean getUserEmail(@PathVariable("email") String email) {
        Boolean result = memberService.getUserEmail(email);
        return result;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        if (authenticationRequest.getUsername().length() == 0) {
            System.out.println("username is NULL");
        }

        if (authenticationRequest.getPassword().length() == 0) {
            System.out.println("Password is NULL");
        }

        System.out.println(authenticationRequest.getUsername() + ", " + authenticationRequest.getPassword());

        Authentication authentication = authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        UserLoginRes userLoginRes = (UserLoginRes) authentication.getPrincipal();

        final String token = jwtTokenUtil.generateToken(userLoginRes);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private Authentication authenticate(String username, String password) {

        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new DisabledException("USER_DISABLED", e);
        } catch (AccountExpiredException e) {
            throw new AccountExpiredException("AccountExpiredException", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("비밀번호 오류 입니다. INVALID_CREDENTIALS", e);
        } catch (InternalAuthenticationServiceException e) {
            throw new InternalAuthenticationServiceException("존재하지 않는 아이디 입니다. InternalAuthenticationServiceException", e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            throw new AuthenticationCredentialsNotFoundException("인증 요구 거부.", e);
        }
    }
}
