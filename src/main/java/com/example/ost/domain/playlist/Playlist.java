package com.example.ost.domain.playlist;

import com.example.ost.domain.track.LikedTrack;
import com.example.ost.domain.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "playlist")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;  // ARTIST / DATE / SIMILAR

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE })
    @JoinTable(
            name="playlist_tracks",
            joinColumns=@JoinColumn(name="playlist_id"),
            inverseJoinColumns=@JoinColumn(name="track_id")
    )
    private List<LikedTrack> tracks = new ArrayList<>();

    protected Playlist() {}

    public Playlist(User user, String name, String type, LocalDateTime createdAt) {
        this.user = user;
        this.name = name;
        this.type = type;
        this.createdAt = createdAt;
    }


    public Long getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public List<LikedTrack> getTracks() { return tracks; }
}
