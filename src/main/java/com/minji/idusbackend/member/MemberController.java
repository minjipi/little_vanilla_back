package com.minji.idusbackend.member;

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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    MemberService memberService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @ResponseBody
    @PostMapping("/signup")
    public BaseResponse<PostMemberRes> createMember(@RequestBody PostMemberReq postMemberReq) {

        try {
            System.out.println("========================== Req: " + postMemberReq);

            PostMemberRes postMemberRes = memberService.createMember(postMemberReq);
            return new BaseResponse<>(postMemberRes);

        } catch (Exception exception) {
            System.out.println(exception);
            return new BaseResponse<>(BaseResponseStatus.FAIL);
        }
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
