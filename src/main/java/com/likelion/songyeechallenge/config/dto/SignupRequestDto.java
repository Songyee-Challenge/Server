package com.likelion.songyeechallenge.config.dto;

import com.likelion.songyeechallenge.domain.user.Role;
import com.likelion.songyeechallenge.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequestDto {
    private String email;
    private String password;
    private String name;
    private String major;
    private Long student_id;
    private Role role;

    public SignupRequestDto(User entity) {
        this.email = entity.getEmail();
        this.password = entity.getPassword();
        this.name = entity.getName();
        this.major = entity.getMajor();
        this.student_id = entity.getStudent_id();
        this.role = entity.getRole();
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .major(major)
                .student_id(student_id)
                .role(this.role)
                .build();
    }
}
