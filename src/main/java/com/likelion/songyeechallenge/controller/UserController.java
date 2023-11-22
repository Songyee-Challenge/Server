package com.likelion.songyeechallenge.controller;

import com.likelion.songyeechallenge.config.dto.AuthResponseDto;
import com.likelion.songyeechallenge.domain.user.User;
import com.likelion.songyeechallenge.config.dto.SignupRequestDto;
import com.likelion.songyeechallenge.service.UserService;
import com.likelion.songyeechallenge.config.dto.LoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequestDto signupRequestDto) {
        User user = userService.join(signupRequestDto);
        return user.getEmail();
    }

    @PostMapping("/signin")
    public String signin(@RequestBody LoginRequestDto loginRequestDto) {
        AuthResponseDto authResponseDto = userService.login(loginRequestDto);
        return authResponseDto.getAccessToken();
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }
}

