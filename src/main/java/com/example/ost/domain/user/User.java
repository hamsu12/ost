package com.example.ost.domain.user;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String spotifyId;

    private String displayName;

    private String profileImage;

    protected User() {}

    public User(String spotifyId, String displayName, String profileImage) {
        this.spotifyId = spotifyId;
        this.displayName = displayName;
        this.profileImage = profileImage;
    }

    public Long getId() { return id; }
    public String getSpotifyId() { return spotifyId; }
    public String getDisplayName() { return displayName; }
    public String getProfileImage() { return profileImage; }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

}
