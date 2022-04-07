package com.minji.idusbackend.favoriteproduct;

import com.minji.idusbackend.config.BaseResponse;
import com.minji.idusbackend.config.BaseResponseStatus;
import com.minji.idusbackend.member.model.UserLoginRes;
import com.minji.idusbackend.product.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping("/cabinet")
public class CabinetController {
    @Autowired
    CabinetProductService cabinetProductService;


    @ResponseBody
    @GetMapping("/")
    public BaseResponse<List<GetCbnProductRes>> cabinetProduct(@AuthenticationPrincipal UserLoginRes userLoginRes) {
        List<GetCbnProductRes> getCbnProductResList = cabinetProductService.cabinetProductList(userLoginRes.getIdx());
        System.out.println("cabinet");
        return new BaseResponse<>(getCbnProductResList);
    }

    @ResponseBody
    @GetMapping("/{idx}")
    public String cbnProduct(@AuthenticationPrincipal UserLoginRes userLoginRes, @PathVariable int idx) {
        return cabinetProductService.cbnProduct(userLoginRes.getIdx(),idx);
    }
}
