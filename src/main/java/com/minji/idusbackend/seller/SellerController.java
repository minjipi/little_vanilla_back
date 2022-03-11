package com.minji.idusbackend.seller;

import com.minji.idusbackend.config.BaseResponse;
import com.minji.idusbackend.config.BaseResponseStatus;
import com.minji.idusbackend.seller.model.PostSellerReq;
import com.minji.idusbackend.seller.model.PostSellerRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000/")
@Controller
@RequestMapping("/seller")

public class SellerController {
    @Autowired
    SellerService sellerService;

    @ResponseBody
    @PostMapping("/signup")
    public BaseResponse<PostSellerRes> createSeller(@RequestBody PostSellerReq postSellerReq) {

        try {
            System.out.println("========================== postSellerReq: " + postSellerReq);
            PostSellerRes postSellerRes = sellerService.createSeller(postSellerReq);
            return new BaseResponse<>(postSellerRes);

        } catch (Exception exception) {
            System.out.println(exception);
            return new BaseResponse<>(BaseResponseStatus.FAIL);
        }
    }
}