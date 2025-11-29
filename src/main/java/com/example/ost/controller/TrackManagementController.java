package com.example.ost.controller;

import com.example.ost.domain.track.LikedTrack;
import com.example.ost.service.TrackManagementService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/track")
public class TrackManagementController {

    private final TrackManagementService service;

    public TrackManagementController(TrackManagementService service) {
        this.service = service;
    }

    // 좋아요
    @PostMapping("/like")
    public String like(@RequestParam String id) {
        service.saveTrack(id);
        return "saved";
    }

    // 좋아요 취소
    @DeleteMapping("/unlike")
    public String unlike(@RequestParam String id) {
        service.removeTrack(id);
        return "removed";
    }

    // 좋아요 목록 조회
    @GetMapping("/liked")
    public List<LikedTrack> likedTracks() {
        return service.getLikedTracks();
    }

    // 1) 가수별 묶음 조회
    @GetMapping("/group/artist")
    public Map<String, List<LikedTrack>> groupByArtist() {
        return service.groupByArtist();
    }

    // 2) 날짜별 묶음 조회
    @GetMapping("/group/date")
    public Map<LocalDate, List<LikedTrack>> groupByDate() {
        return service.groupByDate();
    }
}
