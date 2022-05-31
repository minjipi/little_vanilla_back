package com.minji.idusbackend.pay;

import com.minji.idusbackend.cart.CartDao;
import com.minji.idusbackend.cart.CartService;
import com.minji.idusbackend.config.BaseResponse;
import com.minji.idusbackend.member.model.*;
import com.minji.idusbackend.order.OrderService;
import com.minji.idusbackend.pay.model.PostOrderInfo;
import com.minji.idusbackend.pay.model.PostOrderResponse;
import com.minji.idusbackend.pay.model.PostPayReq;
import com.minji.idusbackend.pay.model.PostPayRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import static com.minji.idusbackend.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/pay")
public class PayController {
    @Autowired
    PayService payService;

    @Autowired
    OrderService orderService;

    @Autowired
    CartService cartService;

    @PostMapping("/complete")
    public BaseResponse<PostOrderResponse> paymentComplete(@RequestBody PostOrderInfo postOrderInfo, @AuthenticationPrincipal UserLoginRes userLoginRes) throws IOException {

        String token = payService.getToken();   // 아임포트에서 정보를 가져오기 위한 토큰 얻어오기
        System.out.println("postOrderInfo : " + postOrderInfo.toString());
        Integer amount = payService.paymentInfo(postOrderInfo.getImpUid(), token);  //얻어온 토큰과 결제 Uid로 결제 금액 정보 가져오기
        try {
            Integer orderPriceCheck = 0;
            for (BigInteger productIdx: postOrderInfo.getIdxList() ) {
                Integer productPrice = orderService.orderPriceCheck(productIdx);  // 주문한 상품 idx로 상품의 실제 가격 가져오기
                orderPriceCheck+=productPrice;
            }
            if (!orderPriceCheck.equals(amount)) {  // 가격 비교해서 일치하지 않으면 에러
                payService.payMentCancle(token, postOrderInfo.getImpUid(), amount, "결제 실패");
                return new BaseResponse<>(PRICE_MISMATCH);
            }

            PostOrderResponse postOrderResponse = null;
            for (BigInteger productIdx: postOrderInfo.getIdxList() ) {
                postOrderResponse = orderService.createOrder(productIdx,  userLoginRes.getIdx(), postOrderInfo.getImpUid());
            }

            //cart
            cartService.cancelCartByMemberIdx(userLoginRes.getIdx());

            // 주문 테이블에 정보 저장, 어떤 상품을 누가 어떻게 결제했나
            return new BaseResponse<>(postOrderResponse);

        } catch (Exception e) {
            System.out.println("결제 에러 : " + e);
            payService.payMentCancle(token, postOrderInfo.getImpUid(), amount, "결제 에러");
            return new BaseResponse<>(FAILED_TO_PAY);
        }
    }


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
