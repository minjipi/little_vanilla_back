package com.minji.idusbackend.order;

import com.minji.idusbackend.config.BaseResponse;

import static com.minji.idusbackend.config.BaseResponseStatus.FAIL;
import com.minji.idusbackend.member.model.UserLoginRes;
import com.minji.idusbackend.order.model.GetOrderList;
import com.minji.idusbackend.order.model.PostOrderReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @ResponseBody
    @PostMapping("/order")
    public BaseResponse<String> order(@AuthenticationPrincipal UserLoginRes userLoginRes, @RequestBody PostOrderReq postOrderReq) {
        System.out.println("postOrderReq: " + postOrderReq);
        String result = orderService.createOrder(userLoginRes.getIdx(), postOrderReq);
        return new BaseResponse<>(result);
    }

    @ResponseBody
    @GetMapping("/cancel/{idx}")
    public BaseResponse<String> cancelOrder(@AuthenticationPrincipal UserLoginRes userLoginRes, @PathVariable int idx) {
        String result = orderService.cancelOrder(userLoginRes.getIdx(), idx);
        return new BaseResponse<>(result);
    }

    @ResponseBody
    @GetMapping("/list")
    public BaseResponse<List<GetOrderList>> orderList(@AuthenticationPrincipal UserLoginRes userLoginRes) {


        try {
            List<GetOrderList> getOrderLists = orderService.orderList(userLoginRes.getIdx());
            System.out.println("userLoginRes.getIdx(): ======"+userLoginRes.getIdx());
            return new BaseResponse<>(getOrderLists);
        } catch (Exception exception) {
            System.out.println("controller exception: " + exception);
            return new BaseResponse<>(FAIL);
        }
    }
}
