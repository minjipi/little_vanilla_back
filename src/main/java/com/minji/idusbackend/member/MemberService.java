package com.minji.idusbackend.member;

import com.minji.idusbackend.config.BaseException;
import com.minji.idusbackend.member.model.*;
import com.minji.idusbackend.seller.PostSellerReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

import static com.minji.idusbackend.config.BaseResponseStatus.DATABASE_ERROR;
import static com.minji.idusbackend.config.BaseResponseStatus.MODIFY_FAIL_USERNAME;

@Service
public class MemberService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MemberDao memberDao;

    @Autowired
    private EmailCertDao emailCertDao;

    public void modifyMemberInfo(PatchMemberModityReq patchMemberModityReq, BigInteger idx) throws BaseException {
        try {
            int result = memberDao.modifyMemberInfo(patchMemberModityReq, idx);

            if (result == 0) {
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public PostMemberRes createSeller(PostSellerReq postSellerReq) {
        postSellerReq.setPassword(passwordEncoder.encode(postSellerReq.getPassword()));
        return memberDao.createSeller(postSellerReq);
    }

    public PostMemberRes createMember(PostMemberReq postMemberReq, String token) {
        postMemberReq.setPassword(passwordEncoder.encode(postMemberReq.getPassword()));
        PostMemberRes postMemberRes = memberDao.createMember(postMemberReq);
        emailCertDao.createToken(new GetEmailCertReq(token, postMemberReq.getEmail()));

        return postMemberRes;
    }


    public Boolean getUserEmail(String email) {
        return memberDao.getUserEmail(email);
    }

}
