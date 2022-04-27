package com.minji.idusbackend.member;

import com.minji.idusbackend.config.BaseException;
import com.minji.idusbackend.config.BaseResponse;
import com.minji.idusbackend.config.BaseResponseStatus;
import com.minji.idusbackend.config.JwtTokenUtil;
import com.minji.idusbackend.member.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.UUID;

import static com.minji.idusbackend.config.BaseResponseStatus.*;
import static com.minji.idusbackend.utils.Validation.isValidatedIdx;

@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    MemberService memberService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private EmailCertService emailCertService;



    @ResponseBody
    @PostMapping("/signup")
    public BaseResponse<PostMemberRes> createMember(@RequestBody PostMemberReq postMemberReq) {


        try {

            String token = UUID.randomUUID().toString();
            System.out.println("========================== Req: " + postMemberReq);

            PostMemberRes postMemberRes = memberService.createMember(postMemberReq, token);
            emailCertService.createEmailConfirmationToken(token, postMemberReq.getEmail());

            return new BaseResponse<>(postMemberRes);

        } catch (Exception exception) {
            System.out.println(exception);
            return new BaseResponse<>(BaseResponseStatus.FAIL);
        }
    }


    @ResponseBody
    @GetMapping("/confirm")
    public BaseResponse<GetEmailCertRes> signupConfirm(GetEmailCertReq getEmailCertReq) throws Exception {
        GetEmailCertRes getEmailCertRes = emailCertService.signupConfirm(getEmailCertReq);
        return new BaseResponse<>(getEmailCertRes);
    }

    @ResponseBody
    @PatchMapping("/modify/{idx}")

//    JWT 확인.
    public BaseResponse<String> modifyMemberInfo(@AuthenticationPrincipal UserLoginRes userLoginRes, @PathVariable("idx") BigInteger idx, @RequestBody PatchMemberModityReq patchMemberModityReq) throws BaseException {

        if (idx == null) {
            return new BaseResponse<>(EMPTY_IDX);
        }
        if (!isValidatedIdx(idx)) {
            return new BaseResponse<>(INVALID_IDX);
        }

        try {
            BigInteger userIdx = userLoginRes.getIdx();
            if (!userIdx.equals(idx)) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            memberService.modifyMemberInfo(patchMemberModityReq);
            String result = patchMemberModityReq.getNickname() + "로 변경 완료.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @PostMapping("/sellersignup")
    public BaseResponse<PostMemberRes> createSeller(@RequestBody PostMemberReq postMemberReq) {

        try {
            System.out.println("========================== Req: " + postMemberReq);

            PostMemberRes postMemberRes = memberService.createSeller(postMemberReq);
            return new BaseResponse<>(postMemberRes);

        } catch (Exception exception) {
            System.out.println(exception);
            return new BaseResponse<>(BaseResponseStatus.FAIL);
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
        System.out.println(authenticationRequest.getUsername());
        System.out.println(authenticationRequest.getPassword());

        Authentication authentication = authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        UserLoginRes userLoginRes = (UserLoginRes) authentication.getPrincipal();

        final String token = jwtTokenUtil.generateToken(userLoginRes);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private Authentication authenticate(String username, String password) throws Exception {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
