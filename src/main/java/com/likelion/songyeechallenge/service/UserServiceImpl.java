package com.likelion.songyeechallenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.likelion.songyeechallenge.user_dto.UserFormDto;
import com.likelion.songyeechallenge.domain.user.User;
import com.likelion.songyeechallenge.domain.user.UserRepository;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Long join(UserFormDto userFormDto) {
        User user = User.builder()
                .email(userFormDto.getEmail())
                .password(userFormDto.getPassword())
                .name(userFormDto.getName())
                .major(userFormDto.getMajor())
                .student_id(userFormDto.getStudent_id())
                .build();
        return userRepository.save(user).getUser_id();
    }

}
