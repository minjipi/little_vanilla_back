package com.minji.idusbackend.member;

import com.minji.idusbackend.member.model.*;
import com.minji.idusbackend.seller.PostSellerReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigInteger;
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

        String createMemberQuery = "(email, password, nickname, phoneNum, gender, birthday, notification) VALUES (?, ?, ?, ?, ?, ?, ?)";

        Object[] createMemberParams = new Object[]{postMemberReq.getEmail(), postMemberReq.getPassword(), postMemberReq.getNickname(), postMemberReq.getPhoneNum(), postMemberReq.getGender(), postMemberReq.getBirthday(), postMemberReq.getNotification()
        };

        this.jdbcTemplate.update(createMemberQuery, createMemberParams);

        String getLastInsertIdxQuery = "select last_insert_id()";
        int lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, int.class);

        String createAuthorityQuery = "insert into authority values(?, ?)";

        Object[] createAuthorityParams = new Object[]{lastInsertIdx, 0};

        this.jdbcTemplate.update(createAuthorityQuery, createAuthorityParams);

        return new PostMemberRes(lastInsertIdx, 1);
    }


    public Integer createMemberKakao(String kakaoemail, String nickname) {

        String createMemberQuery = "insert into Member (email, password, nickname) VALUES (?, ?, ?)";

        Object[] createMemberParams = new Object[]{kakaoemail, "kakao", nickname
        };

        this.jdbcTemplate.update(createMemberQuery, createMemberParams);

        String getLastInsertIdxQuery = "select last_insert_id()";
        int lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, int.class);

        String createAuthorityQuery = "insert into authority values(?, ?)";

        Object[] createAuthorityParams = new Object[]{lastInsertIdx, 0};

        this.jdbcTemplate.update(createAuthorityQuery, createAuthorityParams);

        return lastInsertIdx;
    }


    public PostMemberRes createSeller(PostSellerReq postSellerReq) {

        System.out.println("====="+postSellerReq);

        String createMemberQuery = "insert into seller (email, password, brandname, phoneNum, gender, birthday, notification) VALUES (?, ?, ?, ?, ?, ?, ?)";

        Object[] createMemberParams = new Object[]{postSellerReq.getEmail(), postSellerReq.getPassword(), postSellerReq.getBrandname(), postSellerReq.getPhoneNum(), postSellerReq.getGender(), postSellerReq.getBirthday(), postSellerReq.getNotification()
        };

        this.jdbcTemplate.update(createMemberQuery, createMemberParams);

        String getLastInsertIdxQuery = "select last_insert_id()";
        int lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, int.class);

        String createAuthorityQuery = "insert into authority values(?, ?)";

        Object[] createAuthorityParams = new Object[]{lastInsertIdx, 0};

        this.jdbcTemplate.update(createAuthorityQuery, createAuthorityParams);

        createAuthorityQuery = "insert into authority values(?, ?)";

        createAuthorityParams = new Object[]{lastInsertIdx, 1};

        this.jdbcTemplate.update(createAuthorityQuery, createAuthorityParams);

        return new PostMemberRes(lastInsertIdx, 1);
    }

    public UserLoginRes findByEmail(String email) {
        String getEmailQuery = "SELECT * FROM member LEFT OUTER JOIN authority on member.idx=authority.member_idx WHERE email=? AND status=1";

        return this.jdbcTemplate.queryForObject(getEmailQuery
                , (rs, rowNum) -> new UserLoginRes(
                        rs.getObject("idx", BigInteger.class),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("nickname"),
                        //Arrays.asList(new SimpleGrantedAuthority("ROLE_MEMBER"))
                        Arrays.asList(new SimpleGrantedAuthority(Authority.values()[rs.getObject("role", int.class)].toString()))
                ), email);
    }

    public Boolean getUserEmail(String email) {
        String findEmailQuery = "SELECT * FROM member WHERE email=?";
        try {
            UserLoginRes userLoginRes = this.jdbcTemplate.queryForObject(findEmailQuery
                    , (rs, rowNum) -> new UserLoginRes(
                            rs.getObject("idx", BigInteger.class),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("nickname"),
                            new ArrayList<>()
                    ), email);
            if (userLoginRes.getEmail() != null) {
                return true;

            } else {
                return false;
            }

        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public int checkEmail(String email) {
        String checkEmailQuery = "select exists(select email from member where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);

    }

    public MemberInfo getUserGradeAndPoint(BigInteger idx) {
        String findEmailQuery = "SELECT * FROM member WHERE idx=?";
        return this.jdbcTemplate.queryForObject(findEmailQuery
                , (rs, rowNum) -> new MemberInfo(
                        rs.getString("email"),
                        rs.getString("nickname"),
                        rs.getObject("point", int.class),
                        rs.getString("grade")
                ), idx);
    }

    public void setMemberPoint(BigInteger idx, int point) {
        String plusUserPointQuery = "update member set point = ? where idx = ?";
        Object[] plusUserPointParams = new Object[]{point, idx};
        this.jdbcTemplate.update(plusUserPointQuery, plusUserPointParams);
    }

    public int modifyMemberInfo(PatchMemberModityReq patchMemberModityReq) {

        String modifyMemberQuery = "update Member set nickname=? where idx=?";
        Object[] modifyMemberParams = new Object[]{patchMemberModityReq.getNickname(), patchMemberModityReq.getIdx()
        };

        return this.jdbcTemplate.update(modifyMemberQuery, modifyMemberParams);

    }


}

