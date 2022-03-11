package com.minji.idusbackend.seller;


import com.minji.idusbackend.seller.model.PostSellerReq;
import com.minji.idusbackend.seller.model.PostSellerRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerService {
    @Autowired
    SellerDao sellerDao;

    public PostSellerRes createSeller(PostSellerReq postSellerReq) {
        return sellerDao.createSeller(postSellerReq);
    }
}
