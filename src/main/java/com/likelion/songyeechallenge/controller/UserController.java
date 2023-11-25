package com.likelion.songyeechallenge.controller;

import com.likelion.songyeechallenge.config.MailService;
import com.likelion.songyeechallenge.config.dto.AuthResponseDto;
import com.likelion.songyeechallenge.config.vo.MailVo;
import com.likelion.songyeechallenge.domain.user.User;
import com.likelion.songyeechallenge.config.dto.SignupRequestDto;
import com.likelion.songyeechallenge.service.UserService;
import com.likelion.songyeechallenge.config.dto.LoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@RestController
public class UserController {

    private final UserService userService;
    private final MailService mailService;

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequestDto signupRequestDto) {
        User user = userService.join(signupRequestDto);
        return user.getEmail();
    }

    @ResponseBody
    @PostMapping("/signup/email")
    public String MailSend(@RequestBody MailVo mailVo){
        int number = mailService.sendMail(mailVo.getEmail());
        String num = "" + number;
        return num;
    }

    @ResponseBody
    @PostMapping("/signup/email/verify")
    public String VerifyCode(@RequestBody MailVo mailVo) {
        if (mailVo.getVerificationCode() == mailService.getSavedVerificationCode()) {
            return "Verification successful!";
        } else {
            return "Verification failed. Please check the code.";
        }
    }

    @PostMapping("/signin")
    public String signin(@RequestBody LoginRequestDto loginRequestDto) {
        AuthResponseDto authResponseDto = userService.login(loginRequestDto);
        return authResponseDto.getAccessToken();
    }
}

