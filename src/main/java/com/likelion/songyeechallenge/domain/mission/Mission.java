package com.likelion.songyeechallenge.domain.mission;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.likelion.songyeechallenge.domain.challenge.Challenge;
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

    private boolean isComplete;

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
