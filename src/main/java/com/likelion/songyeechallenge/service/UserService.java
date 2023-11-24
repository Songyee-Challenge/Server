package com.likelion.songyeechallenge.service;

import com.likelion.songyeechallenge.domain.auth.Auth;
import com.likelion.songyeechallenge.domain.auth.AuthRepository;
import com.likelion.songyeechallenge.config.CustomUserDetails;
import com.likelion.songyeechallenge.config.JwtTokenProvider;
import com.likelion.songyeechallenge.config.dto.AuthResponseDto;
import com.likelion.songyeechallenge.domain.challenge.Challenge;
import com.likelion.songyeechallenge.domain.challenge.ChallengeRepository;
import com.likelion.songyeechallenge.domain.likes.Like;
import com.likelion.songyeechallenge.domain.likes.LikeRepository;
import com.likelion.songyeechallenge.domain.mission.Mission;
import com.likelion.songyeechallenge.domain.mission.MissionRepository;
import com.likelion.songyeechallenge.domain.review.Review;
import com.likelion.songyeechallenge.domain.review.ReviewRepository;
import com.likelion.songyeechallenge.domain.user.Role;
import com.likelion.songyeechallenge.domain.user.User;
import com.likelion.songyeechallenge.domain.user.UserRepository;
import com.likelion.songyeechallenge.config.dto.SignupRequestDto;
import com.likelion.songyeechallenge.config.dto.LoginRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final ReviewRepository reviewRepository;
    private final LikeRepository likeRepository;
    private final ChallengeRepository challengeRepository;
    private final MissionRepository missionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public User join(SignupRequestDto signupRequestDto) {
        if (userRepository.findByEmail(signupRequestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 회원가입된 회원입니다.");
        }

        User user = signupRequestDto.toEntity();

        String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(Role.ROLE_USER);

        return userRepository.save(user);
    }

    @Transactional
    public AuthResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("회원가입이 되어 있지 않습니다.")
        );

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀립니다.");
        }

        String accessToken = jwtTokenProvider.generateAccessToken(
                new UsernamePasswordAuthenticationToken(new CustomUserDetails(user), user.getPassword()));
        String refreshToken = jwtTokenProvider.generateRefreshToken(
                new UsernamePasswordAuthenticationToken(new CustomUserDetails(user), user.getPassword()));

        log.info("토큰 내부 확인: {}", accessToken);

        if (authRepository.existsByUser(user)) {
            user.getAuth().updateAccessToken(accessToken);
            user.getAuth().updateRefreshToken(refreshToken);
            return new AuthResponseDto(user.getAuth());
        }

        Auth auth = authRepository.save(Auth.builder()
                .user(user)
                .tokenType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build());

        return new AuthResponseDto(auth);
    }

    @Transactional
    public String refreshToken(String refreshToken) {
        if (this.jwtTokenProvider.validateToken(refreshToken)) {
            Auth auth = this.authRepository.findByRefreshToken(refreshToken).orElseThrow(
                    () -> new IllegalArgumentException("해당 REFRESH_TOKEN 을 찾을 수 없습니다.\nREFRESH_TOKEN = " + refreshToken));

            String newAccessToken = this.jwtTokenProvider.generateAccessToken(
                    new UsernamePasswordAuthenticationToken(
                            new CustomUserDetails(auth.getUser()), auth.getUser().getPassword()));
            auth.updateAccessToken(newAccessToken);
            return newAccessToken;
        }
        return null;
    }

    @Transactional(readOnly = true)
    public User getUserProfile(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다. ID: " + userId));
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다. ID: " + userId));

        // 사용자와 연관된 리뷰 삭제
        List<Review> userReviews = reviewRepository.findByUser(userId);
        for (Review review : userReviews) {
            // 리뷰와 관련된 다양한 데이터 삭제 (예시: 좋아요 등)
            List<Like> reviewLikes = (List<Like>) likeRepository.findByReview(userId);
            likeRepository.deleteAll(reviewLikes);

            // 리뷰 삭제
            reviewRepository.delete(review);
        }

        // 사용자와 연관된 좋아요 데이터 삭제
        List<Like> userLikes = (List<Like>) likeRepository.findByUser(userId);
        likeRepository.deleteAll(userLikes);

        // 사용자와 연관된 챌린지 데이터 삭제
        Set<Challenge> userChallenges = user.getChallenges();
        for (Challenge challenge : userChallenges) {
            // 챌린지와 관련된 다양한 데이터 삭제
            challengeRepository.delete(challenge);
        }

        // 사용자와 연관된 미션 데이터 삭제
        List<Mission> userMissions = missionRepository.findByChallengeId(userId);
        missionRepository.deleteAll(userMissions);

        // 사용자 삭제
        userRepository.delete(user);
    }
}
