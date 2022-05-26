package com.minji.idusbackend.cart;

import com.minji.idusbackend.cart.model.PostCartReq;
import com.minji.idusbackend.config.BaseResponse;
import com.minji.idusbackend.member.model.UserLoginRes;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.minji.idusbackend.config.BaseResponseStatus.FAIL;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

//    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @ResponseBody
    @PostMapping("/in")
    public BaseResponse<String> orderList(@AuthenticationPrincipal UserLoginRes userLoginRes, @RequestBody PostCartReq postCartReq) {
        try {
            String result = cartService.createCart(userLoginRes.getIdx(), postCartReq);

            return new BaseResponse<>(result);
        } catch (Exception exception) {
            System.out.println("controller exception: " + exception);
            return new BaseResponse<>(FAIL);
        }
    }
}

