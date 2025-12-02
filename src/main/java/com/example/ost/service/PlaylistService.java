package com.example.ost.service;

import com.example.ost.domain.playlist.Playlist;
import com.example.ost.domain.track.LikedTrack;
import com.example.ost.domain.user.User;
import com.example.ost.repository.LikedTrackRepository;
import com.example.ost.repository.PlaylistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PlaylistService {


    private final LikedTrackRepository likedTrackRepository;
    private final PlaylistRepository playlistRepository;
    private final UserService userService;

    public PlaylistService(LikedTrackRepository likedTrackRepository,
                           PlaylistRepository playlistRepository,
                           UserService userService) {
        this.likedTrackRepository = likedTrackRepository;
        this.playlistRepository = playlistRepository;
        this.userService = userService;
    }


    @Transactional
    public Playlist createArtistPlaylist(Long userId) {
        User user = userService.getUser(userId);

        List<LikedTrack> liked = likedTrackRepository.findAllByUser(user);

        Map<String, List<LikedTrack>> groups =
                liked.stream().collect(Collectors.groupingBy(LikedTrack::getArtistName));

        Playlist playlist = new Playlist(
                user,
                "Artist Playlist",
                "ARTIST",
                LocalDateTime.now()
        );

        groups.values().forEach(playlist.getTracks()::addAll);

        return playlistRepository.save(playlist);
    }

    @Transactional
    public Playlist createDatePlaylist(Long userId) {
        User user = userService.getUser(userId);

        List<LikedTrack> liked = likedTrackRepository.findAllByUser(user);

        Map<LocalDate, List<LikedTrack>> groups =
                liked.stream().collect(Collectors.groupingBy(t -> t.getLikedAt().toLocalDate()));

        Playlist playlist = new Playlist(
                user,
                "Date Playlist",
                "DATE",
                LocalDateTime.now()
        );

        groups.values().forEach(playlist.getTracks()::addAll);

        return playlistRepository.save(playlist);
    }


    @Transactional(readOnly = true)
    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }
}
