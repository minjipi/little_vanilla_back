package com.minji.idusbackend.favoriteproduct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CabinetProductService {
    @Autowired
    private CabinetProductDao cabinetProductDao;

    public List<GetCbnProductRes> cabinetProductList(int userLoginResIdx){
        return cabinetProductDao.cabinetProductList(userLoginResIdx);
    }

    public String cbnProduct(int userLoginResIdx, int idx){
        return cabinetProductDao.cbnProduct(userLoginResIdx, idx);
    }
}
