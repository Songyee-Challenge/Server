package com.likelion.songyeechallenge.domain.missionUser;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class MissionUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long missionUser_id;

    private Long user_id;

    private Long mission_id;

    private boolean isComplete;

    @Builder
    public MissionUser(Long user_id, Long mission_id, boolean isComplete) {
        this.user_id = user_id;
        this.mission_id = mission_id;
        this.isComplete = isComplete;
    }
}
