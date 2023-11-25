package com.likelion.songyeechallenge.config;

import com.likelion.songyeechallenge.config.vo.MailVo;
import com.likelion.songyeechallenge.config.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/signup")
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
  
    @ResponseBody
    @PostMapping("/email/verify")
    public String VerifyCode(@RequestBody MailVo mailVo) {
        if (mailVo.getVerificationCode() == mailService.getSavedVerificationCode()) {
            return "Verification successful!";
        } else {
            return "Verification failed. Please check the code.";
        }
    }
}