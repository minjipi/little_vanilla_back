package com.minji.idusbackend.pay;

import com.minji.idusbackend.member.MemberDao;
import com.minji.idusbackend.member.model.PostMemberReq;
import com.minji.idusbackend.member.model.PostMemberRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PayService {

    @Autowired
    PayDao payDao;

//    public String chargePay(int userLoginRes, int money) {
//        return payDao.chargePay(userLoginRes, money);
//    }

//    public int showTotalPay(int member_idx){
//        return payDao.showTotalPay(member_idx);
//    }

    public List<PostPayRes> showPayChargeList(int member_idx){
        return payDao.showPayChargeList(member_idx);
    }

    public String chargePay2(int userLoginRes, int money) {
        return payDao.chargePay2(userLoginRes, money);
    }

    public int showTotalPay2(int member_idx){
        return payDao.showTotalPay2(member_idx);
    }

    public String withdrawPay(int userLoginRes, int money) {
        return payDao.withdrawPay(userLoginRes, money);
    }

}