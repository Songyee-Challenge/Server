package com.likelion.songyeechallenge.domain.user;

import com.likelion.songyeechallenge.domain.auth.Auth;
import com.likelion.songyeechallenge.domain.challenge.Challenge;
import com.likelion.songyeechallenge.domain.likes.Like;
import com.likelion.songyeechallenge.domain.mission.Mission;
//import com.likelion.songyeechallenge.domain.mission.MissionUser;
import com.likelion.songyeechallenge.domain.review.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "challenge_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String major;

    @Column(nullable = false)
    private Long student_id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(mappedBy = "participants")
    private Set<Challenge> challenges = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Mission> missions = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Auth auth;

//    @OneToMany(mappedBy = "user")
//    private List<MissionUser> missionUsers = new ArrayList<>();

    @Builder
    public User(Long user_id, String email, String password, String name, String major, Long student_id, Role role) {
        this.user_id = user_id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.major = major;
        this.student_id = student_id;
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getUserId() {
        return user_id;
    }
}
