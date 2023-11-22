package com.likelion.songyeechallenge.controller;

import com.likelion.songyeechallenge.VO.MailVo;
import com.likelion.songyeechallenge.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("user")
public class MailController {

    private final MailService mailService;

    @GetMapping("/mail")
    public String MailPage(){
        return "Mail";
    }

    @ResponseBody
    @PostMapping("/mail")
    public String MailSend(String mail){

        int number = mailService.sendMail(mail);

        String num = "" + number;

        return num;
    }

}