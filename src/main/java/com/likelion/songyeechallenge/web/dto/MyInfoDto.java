package com.likelion.songyeechallenge.web.dto;

import com.likelion.songyeechallenge.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyInfoDto {

    private Long user_id;
    private String name;
    private String email;
    private String major;
    private Long student_id;

    public MyInfoDto(User user) {
        this.user_id = user.getUser_id();
        this.name = user.getName();
        this.email = user.getEmail();
        this.major = user.getMajor();
        this.student_id = user.getStudent_id();
    }
}
