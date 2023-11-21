package com.likelion.songyeechallenge.user_dto;

import com.likelion.songyeechallenge.domain.user.User;
import com.likelion.songyeechallenge.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFormDto {
    private Long user_id;
    private String email;
    private String password;
    private String name;
    private String major;
    private Long student_id;

    public UserFormDto(User entity, UserService userService) {
        this.user_id = entity.getUser_id();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
        this.name = entity.getName();
        this.major = entity.getMajor();
        this.student_id = entity.getStudent_id();
    }
}
