package com.minji.idusbackend.admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@AllArgsConstructor
public class UploadResultDTO implements Serializable {

    private String fileName;

    public String getImageURL(){
        try {
            return URLEncoder.encode(fileName,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

}
