package com.minji.idusbackend.order;

import com.minji.idusbackend.cart.model.GetCart;
import com.minji.idusbackend.config.BaseResponse;

import static com.minji.idusbackend.config.BaseResponseStatus.FAIL;
import com.minji.idusbackend.member.model.UserLoginRes;
import com.minji.idusbackend.order.model.GetOrder;
import com.minji.idusbackend.order.model.PostOrderReq;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @ResponseBody
    @PostMapping("/create")
    public BaseResponse<String> order(@AuthenticationPrincipal UserLoginRes userLoginRes, @RequestBody PostOrderReq postOrderReq) {
        System.out.println("postOrderReq: " + postOrderReq);
        String result = orderService.createOrderBy(userLoginRes.getIdx(), postOrderReq);
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
    public BaseResponse<List<GetOrder>> orderList(@AuthenticationPrincipal UserLoginRes userLoginRes) {
        try {
            List<GetOrder> getOrderList = orderService.orderList(userLoginRes.getIdx());
            System.out.println("userLoginRes: "+userLoginRes.getIdx());
            return new BaseResponse<>(getOrderList);
        } catch (Exception exception) {
            System.out.println("controller exception: " + exception);
            return new BaseResponse<>(FAIL);
        }
    }
}
