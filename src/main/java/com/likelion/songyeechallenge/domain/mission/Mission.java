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

    private boolean isComplete;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Mission(String missionDate, String mission) {
        this.missionDate = missionDate;
        this.mission = mission;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }
}
