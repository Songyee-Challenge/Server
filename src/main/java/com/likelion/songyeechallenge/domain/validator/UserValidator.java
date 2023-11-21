package com.likelion.songyeechallenge.domain.validator;

import com.likelion.songyeechallenge.user_dto.UserFormDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private static final String ALLOWED_EMAIL_DOMAIN = "sookmyung.ac.kr";

    @Override
    public boolean supports(Class<?> clazz) {
        return UserFormDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserFormDto userFormDto = (UserFormDto) target;

        // 이메일이 null이거나 허용되지 않는 도메인이면 오류를 추가
        if (userFormDto.getEmail() == null || !userFormDto.getEmail().endsWith("@" + ALLOWED_EMAIL_DOMAIN)) {
            errors.rejectValue("email", "email.invalid", "유효한 이메일 주소를 입력하세요.");
        }
    }
}
