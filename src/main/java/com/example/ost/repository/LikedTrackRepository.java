package com.example.ost.repository;

import com.example.ost.domain.track.LikedTrack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikedTrackRepository extends JpaRepository<LikedTrack, Long> {

    Optional<LikedTrack> findBySpotifyTrackId(String spotifyTrackId);

    void deleteBySpotifyTrackId(String spotifyTrackId);

    boolean existsBySpotifyTrackId(String spotifyTrackId);
}
