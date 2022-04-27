package com.minji.idusbackend.cbn;

import com.minji.idusbackend.cbn.model.GetCbnProductRes;
import com.minji.idusbackend.cbn.model.GetMyCbnList;
import com.minji.idusbackend.cbn.model.GetOneCbnRes;
import com.minji.idusbackend.cbn.model.PatchCbnRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;


@Service
public class CabinetProductService {
    @Autowired
    private CabinetProductDao cabinetProductDao;

    public String addCbn(BigInteger userLoginResIdx, String cbn_name) {
        return cabinetProductDao.addCbn(userLoginResIdx, cbn_name);
    }

    public List<GetMyCbnList> cbnList(BigInteger userLoginResIdx) {
        return cabinetProductDao.cbnList(userLoginResIdx);
    }

    public List<GetOneCbnRes> getCbn(BigInteger userLoginResIdx, int cabinet_idx) {
        return cabinetProductDao.getCbn(userLoginResIdx, cabinet_idx);
    }

    public PatchCbnRes updateCbn(BigInteger userLoginResIdx, int product_idx, String cbn_idx) throws Exception {
        try {
            return cabinetProductDao.updateCbn(userLoginResIdx, product_idx, cbn_idx);
        } catch (Exception exception) {
            throw new Exception();
        }
    }
}
