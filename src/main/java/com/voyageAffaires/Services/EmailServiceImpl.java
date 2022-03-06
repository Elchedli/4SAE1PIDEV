package com.voyageAffaires.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Service
public class EmailServiceImpl implements EmailService{
    @Autowired
    public JavaMailSender emailSender;

    private MailSender mailSender;
    private SimpleMailMessage templateMessage;

    @Override
    public String send(String to, String subject, String msg) {
        MimeMessage message = emailSender.createMimeMessage();
        boolean multipart = true;
        MimeMessageHelper helper;
        try{
            helper = new MimeMessageHelper(message, multipart, "utf-8");
            message.setContent(msg, "text/html");
            helper.setTo(to);
            helper.setSubject(subject);
            emailSender.send(message);
            return "email has been sent";
        }catch (Exception e){
            return "Error sending the email";
        }



    }
}
