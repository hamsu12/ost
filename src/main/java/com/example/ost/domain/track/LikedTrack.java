package com.example.ost.domain.track;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "liked_track")
public class LikedTrack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Spotify 트랙 ID
    @Column(nullable = false, unique = true)
    private String spotifyTrackId;

    @Column(nullable = false)
    private String trackName;

    @Column(nullable = false)
    private String artistName;

    private String albumName;

    @Column(nullable = false)
    private LocalDateTime likedAt;

    // 기본 생성자 (JPA 필수)
    protected LikedTrack() {
    }

    public LikedTrack(String spotifyTrackId) {
        this.spotifyTrackId = spotifyTrackId;
        this.trackName = "Unknown";
        this.artistName = "Unknown";
        this.albumName = "Unknown";
        this.likedAt = LocalDateTime.now();
    }

    // ==== getter / setter ==== //

    public Long getId() {
        return id;
    }

    public String getSpotifyTrackId() {
        return spotifyTrackId;
    }

    public void setSpotifyTrackId(String spotifyTrackId) {
        this.spotifyTrackId = spotifyTrackId;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public LocalDateTime getLikedAt() {
        return likedAt;
    }

    public void setLikedAt(LocalDateTime likedAt) {
        this.likedAt = likedAt;
    }
}
