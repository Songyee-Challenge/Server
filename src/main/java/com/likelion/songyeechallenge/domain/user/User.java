package com.likelion.songyeechallenge.domain.user;

import com.likelion.songyeechallenge.domain.challenge.Challenge;
import com.likelion.songyeechallenge.domain.likes.Like;
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

    @ManyToMany(mappedBy = "participants")
    private Set<Challenge> challenges = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<Like> likes = new ArrayList<>();

    @Builder
    public User(Long user_id, String email, String password, String name, String major, Long student_id) {
        this.user_id = user_id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.major = major;
        this.student_id = student_id;
    }
}
