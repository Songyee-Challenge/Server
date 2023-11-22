package com.likelion.songyeechallenge.controller;

import com.likelion.songyeechallenge.domain.user.User;
import com.likelion.songyeechallenge.user_dto.UserFormDto;
import com.likelion.songyeechallenge.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("userFormDto", new UserFormDto());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("userFormDto") UserFormDto userFormDto, BindingResult result, RedirectAttributes attributes) {
        try {
            Long userId = userService.join(userFormDto);
            return "redirect:/user/login";
        } catch (Exception e) {
            // 회원가입 실패 시 에러 메시지를 리다이렉트 URL에 추가
            attributes.addFlashAttribute("error", "회원가입에 실패하였습니다.");
            return "redirect:/user/signup";
        }
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

