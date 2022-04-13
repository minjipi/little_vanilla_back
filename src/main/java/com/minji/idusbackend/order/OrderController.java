package com.minji.idusbackend.order;

import com.minji.idusbackend.config.BaseResponse;
import com.minji.idusbackend.config.BaseResponseStatus;
import com.minji.idusbackend.member.model.PostMemberRes;
import com.minji.idusbackend.member.model.UserLoginRes;
import com.minji.idusbackend.order.model.PostOrderReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @ResponseBody
    @PostMapping("/order")
    public BaseResponse<String> order(@AuthenticationPrincipal UserLoginRes userLoginRes, @RequestBody PostOrderReq postOrderReq) {
        System.out.println(postOrderReq);
        String result = orderService.createOrder(userLoginRes.getIdx(), postOrderReq);
        return new BaseResponse<>(result);
    }

    @ResponseBody
    @GetMapping("/cancel/{idx}")
    public BaseResponse<String> cancelOrder(@AuthenticationPrincipal UserLoginRes userLoginRes, @PathVariable int idx) {


        String result = orderService.cancelOrder(userLoginRes.getIdx(), idx);
        return new BaseResponse<>(result);
    }

}
