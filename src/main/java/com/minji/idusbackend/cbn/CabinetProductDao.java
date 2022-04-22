package com.minji.idusbackend.cbn;

import com.minji.idusbackend.cbn.model.GetMyCbnList;
import com.minji.idusbackend.cbn.model.GetOneCbnRes;
import com.minji.idusbackend.cbn.model.PatchCbnRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.util.List;

@Repository
public class CabinetProductDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public String addCbn(int userLoginResIdx, String cbn_name) {
        String addCbnQuery = "insert into cabinet (member_idx, cabinetName) VALUES(?,?)";
        Object[] addCbnParams = new Object[]{userLoginResIdx, cbn_name};
        this.jdbcTemplate.update(addCbnQuery, addCbnParams);
        String getLastInsertIdxQuery = "select last_insert_id()";
        int lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, int.class);
        return "addCbn";
    }

    public List<GetMyCbnList> cbnList(int member_idx) {
        String cbnListQuery = "select * from likes left outer join cabinet on cabinet.idx=likes.cabinet_idx where likes.member_idx=?";

        return this.jdbcTemplate.query(cbnListQuery,
                (rs, rowNum) -> new GetMyCbnList(
                        rs.getObject("member_idx", BigInteger.class),
                        rs.getObject("product_idx", BigInteger.class),
                        rs.getObject("cabinet_idx", BigInteger.class),
                        rs.getObject("idx", BigInteger.class),
                        rs.getString("cabinetName")
                ), member_idx);
    }

    public List<GetOneCbnRes> getCbn(int member_idx, int cabinet_idx) {
        String cbnListQuery = " select * from likes left outer join cabinet on cabinet.idx=likes.cabinet_idx left outer join product on product.idx=likes.product_idx left outer join productImage on productImage.productIdx=product.idx where likes.member_idx=? and cabinet.idx=?";

        return this.jdbcTemplate.query(cbnListQuery,
                (rs, rowNum) -> new GetOneCbnRes(
                        rs.getObject("product_idx", BigInteger.class),
                        rs.getObject("cabinet_idx", BigInteger.class),
                        rs.getObject("idx", BigInteger.class),
                        rs.getString("name"),
                        rs.getObject("brandIdx", BigInteger.class),
                        rs.getObject("categoryIdx", BigInteger.class),
                        rs.getObject("price", BigInteger.class),
                        rs.getObject("salePrice", BigInteger.class),
                        rs.getString("deliveryType"),
                        rs.getString("isTodayDeal"),
                        rs.getString("filename")
                ), member_idx,cabinet_idx);
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
