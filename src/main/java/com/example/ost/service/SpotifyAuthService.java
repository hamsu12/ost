package com.example.ost.service;

import com.example.ost.SpotifyConfig;
import com.example.ost.domain.user.User;
import com.example.ost.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class SpotifyAuthService {

    private final SpotifyConfig config;
    private final RestTemplate rest = new RestTemplate();
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private String accessToken;
    public SpotifyAuthService(SpotifyConfig config, UserRepository userRepository) {
        this.config = config;
        this.userRepository = userRepository;
    }

    public String loginUrl() {
        URI uri = UriComponentsBuilder
                .fromUriString("https://accounts.spotify.com/authorize")
                .queryParam("client_id", config.getClientId())
                .queryParam("response_type", "code")
                .queryParam("redirect_uri", config.getRedirectUri())
                .queryParam("scope", "user-read-email user-read-private")
                .build(true)
                .encode()
                .toUri();

        return uri.toString();
    }

    public String requestToken(String code) {
        String body = "grant_type=authorization_code"
                + "&code=" + code
                + "&redirect_uri=" + config.getRedirectUri();

        String auth = config.getClientId() + ":" + config.getClientSecret();
        String encoded = Base64.getEncoder()
                .encodeToString(auth.getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encoded);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response =
                rest.exchange("https://accounts.spotify.com/api/token",
                        HttpMethod.POST, entity, String.class);

        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            this.accessToken = root.get("access_token").asText();  // ★ 저장
            return this.accessToken;
        } catch (Exception e) {
            throw new RuntimeException("Token parse error", e);
        }
    }

    public String getToken() {
        return this.accessToken;
    }

    public JsonNode getSpotifyProfile(String accessToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response =
                rest.exchange("https://api.spotify.com/v1/me",
                        HttpMethod.GET, entity, String.class);

        try {
            return objectMapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse profile response", e);
        }
    }

    public User handleCallback(String code) {

        String accessToken = requestToken(code);

        JsonNode profile = getSpotifyProfile(accessToken);

        String spotifyId = profile.get("id").asText();
        String displayName =
                profile.has("display_name") &&
                        !profile.get("display_name").isNull()
                        ? profile.get("display_name").asText()
                        : "사용자";

        String imageUrl = null;
        JsonNode images = profile.get("images");
        if (images != null && images.isArray() && images.size() > 0) {
            imageUrl = images.get(0).get("url").asText();
        }

        User user = userRepository.findBySpotifyId(spotifyId)
                .orElse(new User(spotifyId, displayName, imageUrl));

       user.setDisplayName(displayName);
        user.setProfileImage(imageUrl);

        userRepository.save(user);

        return user;
    }
}
