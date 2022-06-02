package com.minji.idusbackend.cart;

import com.minji.idusbackend.cart.model.GetCart;
import com.minji.idusbackend.cart.model.PostCartReq;
import com.minji.idusbackend.cart.model.PostCartRes;
import com.minji.idusbackend.config.BaseResponse;
import com.minji.idusbackend.member.model.UserLoginRes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

import static com.minji.idusbackend.config.BaseResponseStatus.FAIL;
import static com.minji.idusbackend.config.BaseResponseStatus.INVALID_IDX;
import static com.minji.idusbackend.utils.Validation.isValidatedIdx;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    @ResponseBody
    @PostMapping("/in")
    public BaseResponse<PostCartRes> cartIn(@AuthenticationPrincipal UserLoginRes userLoginRes, @RequestBody PostCartReq postCartReq) {

        if (userLoginRes == null) {
            System.out.println("user is NULL.");
        }

        try {
            PostCartRes postCartRes = cartService.createCart(userLoginRes.getIdx(), postCartReq);
            return new BaseResponse<>(postCartRes);
        } catch (Exception exception) {
            System.out.println("controller exception: " + exception);
            return new BaseResponse<>(FAIL);
        }
    }

    @ResponseBody
    @GetMapping("/cancel/{idx}")
    public BaseResponse<PostCartRes> cancelCart(@AuthenticationPrincipal UserLoginRes userLoginRes, @PathVariable BigInteger idx) {
        if (userLoginRes == null) {
            System.out.println("user is NULL.");
        }

        if (!isValidatedIdx(idx)) {
            return new BaseResponse<>(INVALID_IDX);
        }

        try {
            PostCartRes patchCartRes = cartService.cancelCart(userLoginRes.getIdx(), idx);
            return new BaseResponse<>(patchCartRes);
        } catch (Exception exception) {
            System.out.println("controller exception: " + exception);
            return new BaseResponse<>(FAIL);
        }
    }

    @ResponseBody
    @GetMapping("/list")
    public BaseResponse<List<GetCart>> cartList(@AuthenticationPrincipal UserLoginRes userLoginRes) {
        try {
            List<GetCart> getCartLists = cartService.cartList(userLoginRes.getIdx());
            System.out.println("cartController: " + userLoginRes.getIdx());
            return new BaseResponse<>(getCartLists);
        } catch (Exception exception) {
            System.out.println("cartController e: " + exception);
            return new BaseResponse<>(FAIL);
        }
    }
}

