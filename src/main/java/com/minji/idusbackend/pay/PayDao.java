package com.minji.idusbackend.pay;

import com.minji.idusbackend.member.model.Authority;
import com.minji.idusbackend.member.model.PostMemberReq;
import com.minji.idusbackend.member.model.PostMemberRes;
import com.minji.idusbackend.member.model.UserLoginRes;
import com.minji.idusbackend.product.model.GetProductRes;
import com.minji.idusbackend.product.model.ProductImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Repository
public class PayDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public String chargePay(int userLoginRes, int money) {

        // createMemberQuery에 insert~ 쿼리문 저장
        String createMemberQuery = "insert into pay (member_idx, money ) VALUES (?, ?)";

        // {userLoginRes, money}를 Object[] 새 리스트로 만들어서 위 VALUES (?, ?)의 ?에 값 넣음.
        Object[] createMemberParams = new Object[]{userLoginRes, money};

        this.jdbcTemplate.update(createMemberQuery, createMemberParams);

        return "성공";
    }

//    public int showTotalPay(int member_idx) {
//        String showPayQuery = "select sum(money) from pay where member_idx=? group by member_idx";
//
//        return this.jdbcTemplate.queryForObject(showPayQuery,
//                (rs, rowNum) -> rs.getObject("sum(money)", int.class)
//                , member_idx);
//    }

    public int showTotalPay2(int member_idx) {
        String showPayQuery = "select total from pay where member_idx=? order by idx desc limit 1";

        return this.jdbcTemplate.queryForObject(showPayQuery,
                (rs, rowNum) -> rs.getObject("total", int.class)
                , member_idx);
    }

    public List<PostPayRes> showPayChargeList(int member_idx) {
        String showPayQuery = "select * from pay where member_idx=?";

        return this.jdbcTemplate.query(showPayQuery,
                (rs, rowNum) -> new PostPayRes(
                        rs.getObject("idx", int.class),
                        rs.getObject("money", int.class),
                        rs.getDate("created_at")
                ), member_idx);
    }

    public String chargePay2(int userLoginRes, int money) {
        // createMemberQuery에 insert~ 쿼리문 저장
        String createMemberQuery = "insert into pay (member_idx, money, total, in_out) select member_idx, ?, total+?, 1  from pay where member_idx=? order by idx desc limit 1";

        // {userLoginRes, money}를 Object[] 새 리스트로 만들어서 위 VALUES (?, ?)의 ?에 값 넣음.
        Object[] createMemberParams = new Object[]{money, money, userLoginRes};

        this.jdbcTemplate.update(createMemberQuery, createMemberParams);

        return "성공";
    }

    public String withdrawPay(int userLoginRes, int money) {
        // createMemberQuery에 insert~ 쿼리문 저장
        String createMemberQuery = "insert into pay (member_idx, money, total, in_out) select member_idx, ?, total-?, 0  from pay where member_idx=? order by idx desc limit 1";

        // {userLoginRes, money}를 Object[] 새 리스트로 만들어서 위 VALUES (?, ?)의 ?에 값 넣음.
        Object[] createMemberParams = new Object[]{money, money, userLoginRes};

        this.jdbcTemplate.update(createMemberQuery, createMemberParams);

        return "성공";
    }
}
