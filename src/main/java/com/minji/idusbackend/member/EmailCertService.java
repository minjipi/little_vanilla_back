package com.minji.idusbackend.member;

import com.minji.idusbackend.member.model.GetEmailCertReq;
import com.minji.idusbackend.member.model.GetEmailCertRes;
import com.minji.idusbackend.member.model.GetEmailConfirmReq;
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

    @Autowired
    private EmailCertRedisService emailCertRedisService;

    @Async
    public String createEmailConfirmationToken(String token, String receiverEmail, String jwt) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(receiverEmail);
        mailMessage.setSubject("가입 이메일 인증 메일!");
        mailMessage.setText("http://3.39.23.145:8080/member/confirm?email="+receiverEmail+"&token="+token+"&jwt="+jwt );
        javaMailSender.send(mailMessage);

        return "test";
    }

    public GetEmailCertRes signupConfirm(GetEmailConfirmReq getEmailConfirmReq) {

        // if (emailCertDao.tokenCheck(getEmailConfirmReq)) {  // 이부분을 redis에서 찾는 걸로 바꿔야 함.
        if (emailCertRedisService.checkToken(getEmailConfirmReq.getToken(), getEmailConfirmReq.getEmail())) {
            GetEmailCertRes getEmailCertRes = emailCertDao.signupConfirm(getEmailConfirmReq.getEmail());
            return getEmailCertRes;
        }
        else {
            GetEmailCertRes getEmailCertRes = new  GetEmailCertRes(0);
            return getEmailCertRes;
        }

    }
}