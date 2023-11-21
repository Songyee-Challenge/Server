package com.likelion.songyeechallenge.controller;

import com.likelion.songyeechallenge.domain.user.User;
import com.likelion.songyeechallenge.domain.validator.UserValidator;
import com.likelion.songyeechallenge.user_dto.UserFormDto;
import com.likelion.songyeechallenge.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;
    private final UserValidator userValidator;

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @InitBinder("userFormDto")
    public void initBinder(WebDataBinder binder){
        binder.addValidators(userValidator);
    }
    @PostMapping("/signup")
    public String signup(@ModelAttribute("userFormDto") UserFormDto userFormDto, BindingResult result) {
        userValidator.validate(userFormDto, result);

        if (result.hasErrors()) {
            return "signup";
        }

        Long userId = userService.join(userFormDto);
        return "redirect:/login";
    }

}

