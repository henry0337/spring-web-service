package com.example.demo.service;

import com.example.demo.model.MailStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    @Autowired
    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(String targetMail, MailStructure body) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(fromMail);
        mailMessage.setTo(targetMail);
        mailMessage.setSubject(body.getSubject());
        mailMessage.setText(body.getMessage());
        mailMessage.setSentDate(body.getSentDate());

        mailSender.send(mailMessage);
    }
}
