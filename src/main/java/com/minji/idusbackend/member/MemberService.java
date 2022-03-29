package com.minji.idusbackend.member;

import com.minji.idusbackend.member.model.PostMemberReq;
import com.minji.idusbackend.member.model.PostMemberRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MemberDao memberDao;

    public PostMemberRes createMember(PostMemberReq postMemberReq) {
        postMemberReq.setPassword(passwordEncoder.encode(postMemberReq.getPassword()));
        return memberDao.createMember(postMemberReq);
    }

    @Transactional
    public PostMemberRes createSeller(PostMemberReq postMemberReq) {
        postMemberReq.setPassword(passwordEncoder.encode(postMemberReq.getPassword()));
        return memberDao.createSeller(postMemberReq);
    }

    public Boolean getUserEmail(String email) {
        return memberDao.getUserEmail(email);
    }

}
