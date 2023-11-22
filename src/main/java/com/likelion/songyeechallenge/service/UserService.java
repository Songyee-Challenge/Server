package com.likelion.songyeechallenge.service;

import com.likelion.songyeechallenge.user_dto.UserFormDto;

public interface UserService {
    Long join(UserFormDto userFormDto);
    String login(UserFormDto userFormDto);
}
