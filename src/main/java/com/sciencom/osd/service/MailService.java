package com.sciencom.osd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    private JavaMailSender mailSender;

    @Autowired
    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String content){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        simpleMailMessage.setFrom("zcode.noreply@gmail.com");
        MimeMessage message = mailSender.createMimeMessage();
        try{
            InternetAddress sender = new InternetAddress(simpleMailMessage.getFrom(), "LSN");
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(simpleMailMessage.getTo());
            helper.setSubject(simpleMailMessage.getSubject());
            helper.setText(simpleMailMessage.getText(), true);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        mailSender.send(message);
    }

}
