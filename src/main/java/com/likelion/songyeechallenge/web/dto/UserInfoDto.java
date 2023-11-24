package com.likelion.songyeechallenge.web.dto;

import com.likelion.songyeechallenge.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoDto {
private String email;
    private String name;
    private Long student_id;

    public UserInfoDto(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.student_id = user.getStudent_id();
    }

}
