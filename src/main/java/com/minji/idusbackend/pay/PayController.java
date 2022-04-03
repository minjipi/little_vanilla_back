package com.minji.idusbackend.pay;

import com.minji.idusbackend.config.BaseResponse;
import com.minji.idusbackend.config.BaseResponseStatus;
import com.minji.idusbackend.config.JwtTokenUtil;
import com.minji.idusbackend.member.MemberService;
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

import java.util.List;

@RestController
@RequestMapping("/pay")
public class PayController {
    @Autowired
    PayService payService;

//    @ResponseBody
//    @PostMapping("/charge")
//    public BaseResponse<String> chargePay(@AuthenticationPrincipal UserLoginRes userLoginRes, @RequestBody PostPayReq postPayReq) {
//        String result = payService.chargePay(userLoginRes.getIdx(), postPayReq.getMoney());
//        return new BaseResponse<>(result);
//    }

//    @ResponseBody
//    @GetMapping("/showtotal")
//    public BaseResponse<Integer> showTotalPay(@AuthenticationPrincipal UserLoginRes userLoginRes) {
//        int result = payService.showTotalPay(userLoginRes.getIdx());
//        return new BaseResponse<>(result);
//    }

    @ResponseBody
    @GetMapping("/showtotal")
    public BaseResponse<Integer> showTotalPay2(@AuthenticationPrincipal UserLoginRes userLoginRes) {
        int result = payService.showTotalPay2(userLoginRes.getIdx());
        return new BaseResponse<>(result);
    }

    @ResponseBody
    @GetMapping("/showlist")
    public BaseResponse<List<PostPayRes>> showPayChargeList(@AuthenticationPrincipal UserLoginRes userLoginRes) {
        List<PostPayRes> result = payService.showPayChargeList(userLoginRes.getIdx());
        return new BaseResponse<>(result);
    }

    @ResponseBody
    @PostMapping("/charge2")
    public BaseResponse<String> chargePay2(@AuthenticationPrincipal UserLoginRes userLoginRes, @RequestBody PostPayReq postPayReq) {
        String result = payService.chargePay2(userLoginRes.getIdx(), postPayReq.getMoney());
        return new BaseResponse<>(result);
    }

    @ResponseBody
    @PostMapping("/withdrawPay")
    public BaseResponse<String> withdrawPay(@AuthenticationPrincipal UserLoginRes userLoginRes, @RequestBody PostPayReq postPayReq) {
        String result = payService.withdrawPay(userLoginRes.getIdx(), postPayReq.getMoney());
        return new BaseResponse<>(result);
    }
}
