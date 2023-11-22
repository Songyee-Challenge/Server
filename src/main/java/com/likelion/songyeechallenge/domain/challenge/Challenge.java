package com.likelion.songyeechallenge.domain.challenge;

import com.likelion.songyeechallenge.domain.mission.Mission;
import com.likelion.songyeechallenge.domain.picture.Picture;
import com.likelion.songyeechallenge.domain.user.User;
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
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long challenge_id;

    @Column(nullable = false, length = 30)
    private String title;

    private String writer;

    @Column(nullable = false)
    private String startDate;

    @Column(nullable = false)
    private String endDate;

    @Column(nullable = false)
    private String category;

    @Column(columnDefinition = "Text", nullable = false, length = 500)
    private String explain;

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Mission> missions = new ArrayList<>();

    @OneToOne(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Picture picture;

    @ManyToMany
    @JoinTable(
            name = "challenge_participants",
            joinColumns = @JoinColumn(name = "challenge_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> participants = new HashSet<>();

    @Builder
    public Challenge(String title, String writer, String startDate, String endDate, String category, String explain, Picture picture) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        this.writer = writer;
        this.explain = explain;
        this.picture = picture;
    }

    public void setMissions(List<Mission> missionList) {
        this.missions = missionList;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }
}
