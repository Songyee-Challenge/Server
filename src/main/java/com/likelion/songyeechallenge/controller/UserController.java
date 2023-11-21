package com.likelion.songyeechallenge.controller;

import com.likelion.songyeechallenge.domain.user.User;
import com.likelion.songyeechallenge.user_dto.UserFormDto;
import com.likelion.songyeechallenge.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }


    @PostMapping("/signup")
    public String signup(UserFormDto userFormDto) {
        Long userId = userService.join(userFormDto);
        return "redirect:/login";
    }

}

