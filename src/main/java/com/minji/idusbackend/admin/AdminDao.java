package com.minji.idusbackend.admin;

import com.minji.idusbackend.admin.model.OptionDTO;
import com.minji.idusbackend.admin.model.OptionSelectDTO;
import com.minji.idusbackend.admin.model.ProductDTO;
import com.minji.idusbackend.admin.model.ProductImageUploadDTO;
import com.minji.idusbackend.product.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class AdminDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public PostProductRes createProduct(PostProductReq postProductReq) {
        System.out.println(postProductReq.toString());

        String createProductQuery = "insert into Product (name, brandIdx, categoryIdx, price, salePrice, deliveryType, isTodayDeal) VALUES (?, ?, ?, ?, ?, ?, ?)";

        Object[] createProductParams = new Object[]{postProductReq.getName(), postProductReq.getBrandIdx(), postProductReq.getCategoryIdx()
                , postProductReq.getPrice(), postProductReq.getSalePrice(), postProductReq.getDeliveryType(), postProductReq.getIsTodayDeal()};

        this.jdbcTemplate.update(createProductQuery, createProductParams);

        String getLastInsertIdxQuery = "select last_insert_id()";
        int lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, int.class);

        return new PostProductRes(lastInsertIdx, 1);
    }


    public void saveOption(OptionDTO optionDTO) {

    }
    public void saveOptionSelect(OptionSelectDTO optionSelectDTO) {

    }
    public void saveProductImage(ProductImageUploadDTO productImageUploadDTO) {

    }

}
