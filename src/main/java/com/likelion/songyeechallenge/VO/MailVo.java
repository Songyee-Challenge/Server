package com.likelion.songyeechallenge.VO;

import lombok.Data;

@Data
public class MailVo {

    private String email;
    private String title;
    private String content;

    public String getEmail() {
        return email;
    }
}