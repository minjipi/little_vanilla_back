package com.minji.idusbackend.member;

import com.minji.idusbackend.member.model.Authority;
import com.minji.idusbackend.member.model.PostMemberReq;
import com.minji.idusbackend.member.model.PostMemberRes;
import com.minji.idusbackend.member.model.UserLoginRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;

@Repository
public class MemberDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public PostMemberRes createMember(PostMemberReq postMemberReq) {

        String createMemberQuery = "insert into Member (email, password, nickname ) VALUES (?, ?, ?)";

        Object[] createMemberParams = new Object[]{postMemberReq.getEmail(), postMemberReq.getPassword(), postMemberReq.getNickname(),
        };

        this.jdbcTemplate.update(createMemberQuery, createMemberParams);

        String getLastInsertIdxQuery = "select last_insert_id()";
        int lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, int.class);

        String createAuthorityQuery = "insert into authority values(?, ?)";

        Object[] createAuthorityParams = new Object[]{lastInsertIdx, 0};

        this.jdbcTemplate.update(createAuthorityQuery, createAuthorityParams);


        return new PostMemberRes(lastInsertIdx, 1);
    }

    public PostMemberRes createSeller(PostMemberReq postMemberReq) {

        String createMemberQuery = "insert into Member (email, password, nickname ) VALUES (?, ?, ?)";

        Object[] createMemberParams = new Object[]{postMemberReq.getEmail(), postMemberReq.getPassword(), postMemberReq.getNickname(),
        };

        this.jdbcTemplate.update(createMemberQuery, createMemberParams);

        String getLastInsertIdxQuery = "select last_insert_id()";
        int lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, int.class);

        String createAuthorityQuery = "insert into authority values(?, ?)";

        Object[] createAuthorityParams = new Object[]{lastInsertIdx, 1};

        this.jdbcTemplate.update(createAuthorityQuery, createAuthorityParams);


        return new PostMemberRes(lastInsertIdx, 1);
    }

    public UserLoginRes findByEmailAndName(String email) {
        String getEmailQuery = "SELECT * FROM member LEFT OUTER JOIN authority on member.idx=authority.member_idx WHERE email=?";

        return this.jdbcTemplate.queryForObject(getEmailQuery
                , (rs, rowNum) -> new UserLoginRes(
                        rs.getObject("idx", int.class),
                        rs.getString("email"),
                        rs.getString("password"),
                        //Arrays.asList(new SimpleGrantedAuthority("ROLE_MEMBER"))
                        Arrays.asList(new SimpleGrantedAuthority(Authority.values()[rs.getObject("role", int.class)].toString()))
                ), email);
    }

    public Boolean getUserEmail(String email) {

        String findEmailQuery = "SELECT * FROM member WHERE email=?";

        try {
            UserLoginRes userLoginRes = this.jdbcTemplate.queryForObject(findEmailQuery
                    , (rs, rowNum) -> new UserLoginRes(
                            rs.getObject("idx", int.class),
                            rs.getString("email"),
                            rs.getString("password"),
                            new ArrayList<>()
                    ), email);
            if (userLoginRes.getEmail() != null) {
                return true;

            } else {
                return false;
            }

        }catch (EmptyResultDataAccessException e) {
            return false;

        }
    }

}

