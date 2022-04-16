package com.minji.idusbackend.cbn;

import com.minji.idusbackend.cbn.model.GetCbnProductRes;
import com.minji.idusbackend.cbn.model.PatchCbnRes;
import com.minji.idusbackend.config.BaseResponse;
import com.minji.idusbackend.config.BaseResponseStatus;
import com.minji.idusbackend.member.model.UserLoginRes;
import com.minji.idusbackend.product.model.PatchProductRes;
import com.minji.idusbackend.product.model.PostProductReq;
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
    @GetMapping("/list/{idx}")
    public BaseResponse<List<GetCbnProductRes>> cabinetProduct(@AuthenticationPrincipal UserLoginRes userLoginRes) {
        List<GetCbnProductRes> getCbnProductResList = cabinetProductService.cabinetProductList(userLoginRes.getIdx());
        return new BaseResponse<>(getCbnProductResList);
    }

    @ResponseBody
    @GetMapping("/add/{cbn_name}")
    public String addCbn(@AuthenticationPrincipal UserLoginRes userLoginRes, @PathVariable String cbn_name) {
        return cabinetProductService.addCbn(userLoginRes.getIdx(), cbn_name);
    }

    @ResponseBody
    @PatchMapping("/update/{product_idx}/{cbn_idx}")
    public BaseResponse<PatchCbnRes> updateCbn(@AuthenticationPrincipal UserLoginRes userLoginRes, @PathVariable int product_idx, @PathVariable String cbn_idx) {
        try {
            PatchCbnRes patchCbnRes = cabinetProductService.updateCbn(userLoginRes.getIdx(), product_idx, cbn_idx);
            return new BaseResponse<>(patchCbnRes);
        } catch (Exception exception) {
            System.out.println(exception);
            return new BaseResponse<>(BaseResponseStatus.FAIL);
        }
    }
}
