package com.likelion.songyeechallenge.domain.mission;

import com.likelion.songyeechallenge.domain.challenge.Challenge;
import com.likelion.songyeechallenge.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mission_id;

    @Column(nullable = false)
    private String missionDate;

    @Column(nullable = false)
    private String mission;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @Builder
    public Mission(String missionDate, String mission) {
        this.missionDate = missionDate;
        this.mission = mission;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }
}
