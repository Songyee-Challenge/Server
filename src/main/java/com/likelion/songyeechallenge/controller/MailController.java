package com.likelion.songyeechallenge.controller;

import com.likelion.songyeechallenge.VO.MailVo;
import com.likelion.songyeechallenge.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/signup")
public class MailController {

    private final MailService mailService;

    @GetMapping("/email")
    public String MailPage(){
        return "Mail";
    }

    @ResponseBody
    @PostMapping("/email")
    public String MailSend(@RequestBody MailVo mailVo){

        int number = mailService.sendMail(mailVo.getEmail());

        String num = "" + number;

        return num;
    }

}