package com.example.ost.repository;

import com.example.ost.domain.playlist.Playlist;
import com.example.ost.domain.track.LikedTrack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    List<Playlist> findAllByTracksContaining(LikedTrack track);

}
