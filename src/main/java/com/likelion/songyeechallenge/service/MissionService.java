package com.likelion.songyeechallenge.service;

import com.likelion.songyeechallenge.domain.challenge.Challenge;
import com.likelion.songyeechallenge.domain.mission.Mission;
import com.likelion.songyeechallenge.domain.mission.MissionRepository;
import com.likelion.songyeechallenge.web.dto.MissionSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MissionService {

    private final MissionRepository missionRepository;

    @Transactional
    public List<Mission> uploadMission(List<MissionSaveRequestDto> missionDtoList, Challenge challenge) {
        List<Mission> missions = new ArrayList<>();

        for (MissionSaveRequestDto missionDto : missionDtoList) {
            Mission mission = missionDto.toEntityMission();
            mission.setChallenge(challenge);
            missions.add(missionRepository.save(mission));
        }
        return missions;
    }
}
