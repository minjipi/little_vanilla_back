package com.minji.idusbackend.admin;

import com.minji.idusbackend.admin.model.OptionDTO;
import com.minji.idusbackend.admin.model.OptionSelectDTO;
import com.minji.idusbackend.admin.model.ProductDTO;
import com.minji.idusbackend.admin.model.ProductImageUploadDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminService {
    @Autowired
    AdminDao adminDao;
    public void saveService(ProductDTO productDTO) {
        // dao로 상품 저장

        List<OptionDTO> optionDTOList = productDTO.getOptionDTOList();
        if (optionDTOList.size() > 0) {
            for (int i = 0; i < optionDTOList.size(); i++) {
                OptionDTO optionDTO = optionDTOList.get(i);

                // 옵션 저장


                List<OptionSelectDTO> optionSelectDTOList = optionDTO.getOptionSelectDTOList();
                if (optionSelectDTOList.size() > 0) {
                    for (int j = 0; j < optionSelectDTOList.size(); j++) {
                        OptionSelectDTO optionSelectDTO = optionSelectDTOList.get(j);

                        //옵션셀렉트 저
                    }
                }
            }
        }

        List<ProductImageUploadDTO> productImageUploadDTOList = productDTO.getProductImageUploadDTOList();
        if (productImageUploadDTOList.size() > 0) {
            for (int i = 0; i < productImageUploadDTOList.size(); i++) {

                ProductImageUploadDTO productImageUploadDTO = productImageUploadDTOList.get(i);

                //상품 이미지 저장
            }
        }


    }


}
