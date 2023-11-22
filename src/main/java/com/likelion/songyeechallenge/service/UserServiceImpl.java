package com.likelion.songyeechallenge.service;

import org.springframework.context.annotation.Bean;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.likelion.songyeechallenge.user_dto.UserFormDto;
import com.likelion.songyeechallenge.domain.user.User;
import com.likelion.songyeechallenge.domain.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public Long join(UserFormDto userFormDto) {
        // 이메일 중복 체크
        if (userRepository.findByEmail(userFormDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일 주소입니다.");
        }
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(userFormDto.getPassword());


        User user = User.builder()
                .user_id(userFormDto.getUser_id())
                .email(userFormDto.getEmail())
                .password(userFormDto.getPassword())
                .name(userFormDto.getName())
                .major(userFormDto.getMajor())
                .student_id(userFormDto.getStudent_id())
                .build();

        userRepository.save(user);
        return user.getUser_id();
    }

    @Override
    public String login(UserFormDto userFormDto) {
        // 이메일로 사용자 조회
        Optional<User> optionalUser = userRepository.findByEmail(userFormDto.getEmail());

        // 사용자가 존재하고 비밀번호가 일치하면 로그인 성공
        if (optionalUser.isPresent() && passwordEncoder.matches(userFormDto.getPassword(), optionalUser.get().getPassword())) {
            return "success";
        }

        // 로그인 실패
        throw new IllegalArgumentException("로그인에 실패했습니다. 이메일 또는 비밀번호를 확인하세요.");
    }
}
