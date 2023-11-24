//package com.likelion.songyeechallenge.domain.mission;
//
//import com.likelion.songyeechallenge.domain.user.User;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Getter
//@NoArgsConstructor
//@Entity
//public class MissionUser {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "mission_id")
//    private Mission mission;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    private boolean isComplete;
//
//    @Builder
//    public MissionUser(Mission mission, User user, boolean isComplete) {
//        this.mission = mission;
//        this.user = user;
//        this.isComplete = isComplete;
//    }
//}
