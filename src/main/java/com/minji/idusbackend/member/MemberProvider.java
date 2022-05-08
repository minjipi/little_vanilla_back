package com.minji.idusbackend.member;

import com.minji.idusbackend.config.BaseException;
import com.minji.idusbackend.member.model.UserLoginRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.minji.idusbackend.config.BaseResponseStatus.*;

@Service
public class MemberProvider {
    private final MemberDao memberDao;

    @Autowired
    public MemberProvider(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

// provider 사용이유!!!! memberService에서 패스워드인코더 참조해서... 순환참조 문제 때문에.

//    public GetMemberRes getUser(BigInteger idx) throws BaseException {
//        if (memberDao.isNotExistedUser(idx)) {
//            throw new BaseException(RESPONSE_NULL_ERROR_BY_IDX);
//        }
//
//        try {
//            GetMemberRes getMemberRes = memberDao.getUser(idx);
//            // if (getUserRes == null)
//            return getMemberRes;
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }

    public int checkEmail(String email) throws BaseException{
        try{
            return memberDao.checkEmail(email);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public UserLoginRes findByEmail(String email){
        try{
            return memberDao.findByEmail(email);
        } catch (Exception exception){
            throw exception;
        }
    }


}
