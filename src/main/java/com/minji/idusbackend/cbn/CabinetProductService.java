package com.minji.idusbackend.cbn;

import com.minji.idusbackend.cbn.model.GetCbnProductRes;
import com.minji.idusbackend.cbn.model.PatchCbnRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CabinetProductService {
    @Autowired
    private CabinetProductDao cabinetProductDao;

    public List<GetCbnProductRes> cabinetProductList(int userLoginResIdx) {
        return cabinetProductDao.cbnProductList(userLoginResIdx);
    }

    public String addCbn(int userLoginResIdx, String cbn_name) {
        return cabinetProductDao.addCbn(userLoginResIdx, cbn_name);
    }

    public PatchCbnRes updateCbn(int userLoginResIdx, int product_idx, String cbn_idx) throws Exception {
        try {
            return cabinetProductDao.updateCbn(userLoginResIdx, product_idx, cbn_idx);
        } catch (Exception exception) {
            throw new Exception();
        }
    }
}
