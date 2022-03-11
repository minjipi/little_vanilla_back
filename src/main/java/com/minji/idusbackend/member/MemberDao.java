package com.minji.idusbackend.member;

import com.minji.idusbackend.member.model.PostMemberReq;
import com.minji.idusbackend.member.model.PostMemberRes;
import com.minji.idusbackend.member.model.UserLoginRes;
import com.minji.idusbackend.product.model.GetProductRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public PostMemberRes createMember(PostMemberReq postMemberReq) {

        String createMemberQuery = "insert into Member (email, password, nickname) VALUES (?, ?, ?)";

        Object[] createMemberParams = new Object[]{postMemberReq.getEmail(), postMemberReq.getPassword(), postMemberReq.getNickname(),
        };

        this.jdbcTemplate.update(createMemberQuery, createMemberParams);

        String getLastInsertIdxQuery = "select last_insert_id()";
        int lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, int.class);

        return new PostMemberRes(lastInsertIdx, 1);
    }

    public UserLoginRes findByEmailAndName(String email){
        String getEmailQuery = "SELECT * FROM member WHERE email=?";

        return this.jdbcTemplate.queryForObject(getEmailQuery
                , (rs, rowNum) -> new UserLoginRes(
                        rs.getObject("idx", int.class),
                        rs.getString("email"),
                        rs.getString("password"),
                        new ArrayList<>()
                ), email);

    }


}

