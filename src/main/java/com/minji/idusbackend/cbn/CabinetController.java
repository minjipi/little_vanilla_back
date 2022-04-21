package com.minji.idusbackend.cbn;

import com.minji.idusbackend.cbn.model.GetCbnProductRes;
import com.minji.idusbackend.cbn.model.GetMyCbnList;
import com.minji.idusbackend.cbn.model.GetOneCbnRes;
import com.minji.idusbackend.cbn.model.PatchCbnRes;
import com.minji.idusbackend.config.BaseResponse;
import com.minji.idusbackend.config.BaseResponseStatus;
import com.minji.idusbackend.member.model.UserLoginRes;
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
    @GetMapping("/add/{cbn_name}")
    public String addCbn(@AuthenticationPrincipal UserLoginRes userLoginRes, @PathVariable String cbn_name) {
        return cabinetProductService.addCbn(userLoginRes.getIdx(), cbn_name);
    }

    @ResponseBody
    @GetMapping("/cbnlist")
    public List<GetMyCbnList> cbnList(@AuthenticationPrincipal UserLoginRes userLoginRes) {
        return cabinetProductService.cbnList(userLoginRes.getIdx());
    }

    @ResponseBody
    @GetMapping("/{cabinet_idx}")
    public List<GetOneCbnRes> getCbn(@AuthenticationPrincipal UserLoginRes userLoginRes, @PathVariable int cabinet_idx) {
        return cabinetProductService.getCbn(userLoginRes.getIdx(), cabinet_idx);
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
