package com.minji.idusbackend.cbn;

import com.minji.idusbackend.cbn.model.GetCbnProductRes;
import com.minji.idusbackend.cbn.model.PatchCbnRes;
import com.minji.idusbackend.product.model.PatchProductRes;
import com.minji.idusbackend.product.model.PostProductRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CabinetProductDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetCbnProductRes> cbnProductList(int member_idx) {
        String getCbnQuery = "select * from likes left outer join product on product.idx=likes.product_idx left outer join productImage on productImage.productIdx=product.idx where cabinet_idx is NULL and member_idx=?";

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
                        rs.getString("filename")
                ), member_idx);
    }

    public String addCbn(int userLoginResIdx, String cbn_name) {
        String addCbnQuery = "insert into cabinet (member_idx, cabinetName) VALUES(?,?)";
        Object[] addCbnParams = new Object[]{userLoginResIdx, cbn_name};
        this.jdbcTemplate.update(addCbnQuery, addCbnParams);
        String getLastInsertIdxQuery = "select last_insert_id()";
        int lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, int.class);
        return "addCbn";

    }

    public PatchCbnRes updateCbn(int userLoginResIdx, int product_idx, String cbn_idx) {
        String updateCbnQuery = "update likes set cabinet_idx=? where member_idx=? and product_idx=?;";

        Object[] updateCbnParams = new Object[]{cbn_idx, userLoginResIdx, product_idx};

        this.jdbcTemplate.update(updateCbnQuery, updateCbnParams);

        String getLastInsertIdxQuery = "select last_insert_id()";

        int lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, int.class);

        return new PatchCbnRes(lastInsertIdx, 0);
    }
}
