package com.likelion.songyeechallenge.domain.picture;

import com.likelion.songyeechallenge.domain.challenge.Challenge;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long picture_id;

    private String originalName;
    private String newName;

    @OneToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @Builder
    public Picture(String originalName, String newName) {
        this.originalName = originalName;
        this.newName = newName;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }
}
