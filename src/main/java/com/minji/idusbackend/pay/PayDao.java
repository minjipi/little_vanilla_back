package com.minji.idusbackend.pay;

import com.minji.idusbackend.pay.model.PostPayRes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class PayDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public String chargePay(BigInteger userLoginRes, int money) {

        // createMemberQuery에 insert~ 쿼리문 저장
        String createMemberQuery = "insert into pay (member_idx, money ) VALUES (?, ?)";

        // {userLoginRes, money}를 Object[] 새 리스트로 만들어서 위 VALUES (?, ?)의 ?에 값 넣음.
        Object[] createMemberParams = new Object[]{userLoginRes, money};

        this.jdbcTemplate.update(createMemberQuery, createMemberParams);

        return "성공";
    }

    public int showTotalPay2(BigInteger member_idx) {
        String showPayQuery = "select total from pay where member_idx=? order by idx desc limit 1";

        return this.jdbcTemplate.queryForObject(showPayQuery,
                (rs, rowNum) -> rs.getObject("total", int.class)
                , member_idx);
    }

    public List<PostPayRes> showPayChargeList(BigInteger member_idx) {
        String showPayQuery = "select * from pay where member_idx=?";

        return this.jdbcTemplate.query(showPayQuery,
                (rs, rowNum) -> new PostPayRes(
                        rs.getObject("idx", int.class),
                        rs.getObject("money", int.class),
                        rs.getDate("created_at")
                ), member_idx);
    }

    public String chargePay2(BigInteger userLoginRes, int money) {
        String createMemberQuery = "insert into pay (member_idx, money, total, in_out) select member_idx, ?, total+?, 1  from pay where member_idx=? order by idx desc limit 1";

        Object[] createMemberParams = new Object[]{money, money, userLoginRes};

        this.jdbcTemplate.update(createMemberQuery, createMemberParams);

        return "성공";
    }

    public String withdrawPay(BigInteger userLoginRes, int money) {
        String createMemberQuery = "insert into pay (member_idx, money, total, in_out) select member_idx, ?, total-?, 0  from pay where member_idx=? order by idx desc limit 1";

        Object[] createMemberParams = new Object[]{money, money, userLoginRes};

        this.jdbcTemplate.update(createMemberQuery, createMemberParams);

        return "성공";
    }
}

