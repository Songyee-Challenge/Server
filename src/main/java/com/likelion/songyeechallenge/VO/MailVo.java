package com.likelion.songyeechallenge.VO;

import lombok.Data;

@Data
public class MailVo {

    private String email;
    private String title;
    private String content;

    // 인증번호 재확인
    private int verificationCode;

    public String getEmail() {
        return email;
    }
}