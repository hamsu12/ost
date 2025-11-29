package com.example.ost.dto;

public class SpotifyTrackInfoDto {
    private final String id;
    private final String name;
    private final String artistName;
    private final String albumName;
    private final String imageUrl;

    public SpotifyTrackInfoDto(String id, String name, String artistName, String albumName, String imageUrl) {
        this.id = id;
        this.name = name;
        this.artistName = artistName;
        this.albumName = albumName;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getAlbumName() {
        return albumName;
    }
    public String getImageUrl() { return imageUrl; }
}
