package com.example.ost.service;

import com.example.ost.domain.user.User;
import com.example.ost.repository.UserRepository;
import com.example.ost.repository.PlaylistRepository;
import com.example.ost.repository.LikedTrackRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository,
                       LikedTrackRepository likedTrackRepository,
                       PlaylistRepository playlistRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public User registerOrLogin(String spotifyId, String name, String image) {
        return userRepository.findBySpotifyId(spotifyId)
                .orElseGet(() -> userRepository.save(new User(spotifyId, name, image)));
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(user);
    }

}
