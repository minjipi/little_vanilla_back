package com.minji.idusbackend.admin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProductImageUploadDTO {
    private String imgName;

    public String getImageURL() {
        try {
            return URLEncoder.encode("/" + imgName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
