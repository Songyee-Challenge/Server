package com.likelion.songyeechallenge.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private static final String senderEmail= "gohwangbong@gmail.com";
    private static int number;
    private int savedVerificationCode;
    public static void createNumber(){
        number = (int)(Math.random() * (90000)) + 100000;// (int) Math.random() * (최댓값-최소값+1) + 최소값
    }

    public MimeMessage CreateMail(String mail){
        createNumber();
        savedVerificationCode = number;
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            Multipart multipart = new MimeMultipart("related");
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, mail);
            message.setSubject("송이의숲 회원가입 이메일 인증번호");

            double imageWidth = 951;
            int imageHeight = 540;
            String body = "";
            body += String.format("<img src=\"https://ifh.cc/g/MTkbzQ.jpg\" alt='Image' width='%.2f' height='%d'>", imageWidth, imageHeight);
            body += "<h3>" + "요청하신 인증 번호는" + "</h3>";
            body += "<h1>" + number + "</h1>";
            body += "<h3>" + "입니다. 감사합니다." + "</h3>";
            messageHelper.setText(body,true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return message;
    }

    public int sendMail(String mail){
        MimeMessage message = CreateMail(mail);
        javaMailSender.send(message);
        return number;
    }

    public int getSavedVerificationCode() {
        return savedVerificationCode;
    }
}