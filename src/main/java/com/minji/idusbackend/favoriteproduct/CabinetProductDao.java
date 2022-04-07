package com.minji.idusbackend.favoriteproduct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class CabinetProductDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetCbnProductRes> cabinetProductList (int member_idx) {
        String getCbnQuery = "SELECT * FROM cabinet LEFT OUTER JOIN product ON cabinet.product_idx=product.idx WHERE cabinet.member_idx=?";

        return this.jdbcTemplate.query(getCbnQuery,
                (rs, rowNum) -> new GetCbnProductRes(
                        rs.getObject("idx", int.class),
                        rs.getString("name"),
                        rs.getObject("brandIdx", int.class),
                        rs.getObject("categoryIdx", int.class),
                        rs.getObject("price", int.class),
                        rs.getObject("salePrice", int.class),
                        rs.getString("deliveryType"),
                        rs.getString("isTodayDeal"),
                        rs.getString("filename"),
                        rs.getBoolean("like_ckeck")
                ),member_idx);
    }


    public String cbnProduct(int userLoginResIdx, int idx) {

        System.out.println(userLoginResIdx);
        System.out.println(idx);

        // 좋아요를 누른 적이 있나 확인
        String getLikeQuery = "select * from cabinet where member_idx=? and product_idx=?";
        List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(getLikeQuery, userLoginResIdx, idx);

        for(Map<String, Object> row : rows){
            int member_idx = Integer.parseInt(row.get("member_idx").toString());
            int product_idx = Integer.parseInt(row.get("product_idx").toString());

            System.out.println(member_idx + " " + product_idx);
        }

        // 없으면 추가
        if (rows.size() == 0) {
            String createProductQuery = "insert into cabinet (member_idx, product_idx) VALUES (?, ?)";

            Object[] createProductParams = new Object[]{userLoginResIdx, idx};

            this.jdbcTemplate.update(createProductQuery, createProductParams);

            String getLastInsertIdxQuery = "select last_insert_id()";
            int lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, int.class);

            return "added";
        } else {
            // 있으면 제거
            String createProductQuery = "DELETE FROM cabinet WHERE member_idx=? and product_idx=?";

            Object[] createProductParams = new Object[]{userLoginResIdx, idx};

            this.jdbcTemplate.update(createProductQuery, createProductParams);

            String getLastInsertIdxQuery = "select last_insert_id()";
            int lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, int.class);

            return "deleted";
        }
    }

}
