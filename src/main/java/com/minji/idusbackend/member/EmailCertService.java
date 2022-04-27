package com.minji.idusbackend.member;

import com.minji.idusbackend.member.model.GetEmailCertReq;
import com.minji.idusbackend.member.model.GetEmailCertRes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailCertService {

    private final
    JavaMailSender javaMailSender;

    @Autowired
    private EmailCertDao emailCertDao;

    @Async
    public String createEmailConfirmationToken(String token, String receiverEmail) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(receiverEmail);
        mailMessage.setSubject("회원가입 이메일 인증");
        mailMessage.setText("http://localhost:8080/member/confirm?email="+receiverEmail+"&token="+token);
        javaMailSender.send(mailMessage);

        return "test";
    }

    public GetEmailCertRes signupConfirm(GetEmailCertReq getEmailCertReq) {
        if (emailCertDao.tokenCheck(getEmailCertReq)) {
            GetEmailCertRes getEmailCertRes = emailCertDao.signupConfirm(getEmailCertReq.getEmail());
            return getEmailCertRes;
        }
        else {
            GetEmailCertRes getEmailCertRes = new  GetEmailCertRes(0);
            return getEmailCertRes;
        }

    }
}