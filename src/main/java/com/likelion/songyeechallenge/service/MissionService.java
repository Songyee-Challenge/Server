package com.likelion.songyeechallenge.service;

import com.likelion.songyeechallenge.domain.challenge.Challenge;
import com.likelion.songyeechallenge.domain.mission.Mission;
import com.likelion.songyeechallenge.domain.mission.MissionRepository;
import com.likelion.songyeechallenge.web.dto.MissionSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MissionService {

    private final MissionRepository missionRepository;

    @Transactional
    public List<Mission> saveMission(List<MissionSaveRequestDto> missionDtoList, Challenge challenge) {
        List<Mission> missions = missionDtoList.stream()
                .map(dto -> {
                     Mission mission = dto.toEntityMission();
                     mission.setChallenge(challenge);
                     return mission;
                })
                .sorted(Comparator.comparing(Mission::getMissionDate))
                .collect(Collectors.toList());
        return missionRepository.saveAll(missions);
    }
}
