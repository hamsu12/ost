package com.example.ost.repository;

import com.example.ost.domain.track.LikedTrack;
import com.example.ost.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikedTrackRepository extends JpaRepository<LikedTrack, Long> {

    Optional<LikedTrack> findByUserAndSpotifyTrackId(User user, String spotifyTrackId);

    List<LikedTrack> findAllByUser(User user);

    boolean existsByUserAndSpotifyTrackId(User user, String spotifyTrackId);
    void deleteAllByUser(User user);
}
