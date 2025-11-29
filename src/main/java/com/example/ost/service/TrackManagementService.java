package com.example.ost.service;

import com.example.ost.domain.track.LikedTrack;
import com.example.ost.repository.LikedTrackRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TrackManagementService {

    private final LikedTrackRepository likedTrackRepository;

    public TrackManagementService(LikedTrackRepository likedTrackRepository) {
        this.likedTrackRepository = likedTrackRepository;
    }

    @Transactional
    public void saveTrack(String spotifyTrackId) {
        if (likedTrackRepository.existsBySpotifyTrackId(spotifyTrackId)) {
            return;
        }

        LikedTrack track = new LikedTrack(spotifyTrackId); // 1개짜리 생성자 사용
        likedTrackRepository.save(track);
    }


    /**
     * 좋아요 취소
     */
    @Transactional
    public void removeTrack(String spotifyTrackId) {
        likedTrackRepository.deleteBySpotifyTrackId(spotifyTrackId);
    }

    /**
     * 좋아요 전체 목록
     */
    @Transactional(readOnly = true)
    public List<LikedTrack> getLikedTracks() {
        return likedTrackRepository.findAll();
    }

    /**
     * 같은 아티스트끼리 묶기
     * key: artistName, value: 그 가수 곡 리스트
     */
    @Transactional(readOnly = true)
    public Map<String, List<LikedTrack>> groupByArtist() {
        List<LikedTrack> tracks = likedTrackRepository.findAll();
        return tracks.stream()
                .collect(Collectors.groupingBy(LikedTrack::getArtistName));
    }

    /**
     * 좋아요 누른 날짜별로 묶기
     * key: LocalDate(YYYY-MM-DD), value: 그날 좋아요한 곡 리스트
     */
    @Transactional(readOnly = true)
    public Map<LocalDate, List<LikedTrack>> groupByDate() {
        List<LikedTrack> tracks = likedTrackRepository.findAll();
        return tracks.stream()
                .collect(Collectors.groupingBy(t -> t.getLikedAt().toLocalDate()));
    }
}
