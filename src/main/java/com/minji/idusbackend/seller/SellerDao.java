package com.minji.idusbackend.seller;
import com.minji.idusbackend.seller.model.PostSellerReq;
import com.minji.idusbackend.seller.model.PostSellerRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class SellerDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public PostSellerRes createSeller(PostSellerReq postSellerReq) {

        String createSellerQuery = "insert into Seller (id, password, nickname, grade, birthday, gender) VALUES (?, ?, ?, ?, ?, ?)";

        Object[] createSellerParams = new Object[]{postSellerReq.getId(), postSellerReq.getPassword(), postSellerReq.getNickname()
                , postSellerReq.getGrade(), postSellerReq.getBirthday(), postSellerReq.getGender()};

        this.jdbcTemplate.update(createSellerQuery, createSellerParams);

        String getLastInsertIdxQuery = "select last_insert_id()";
        int lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, int.class);

        return new PostSellerRes(lastInsertIdx, 1);
    }
}
