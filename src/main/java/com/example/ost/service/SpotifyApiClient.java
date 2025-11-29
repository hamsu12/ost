package com.example.ost.service;

import com.example.ost.SpotifyConfig;
import com.example.ost.dto.SpotifyTrackInfoDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Service
public class SpotifyApiClient {

    private final RestTemplate restTemplate;
    private final SpotifyConfig spotifyConfig;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SpotifyApiClient(RestTemplate restTemplate, SpotifyConfig spotifyConfig) {
        this.restTemplate = restTemplate;
        this.spotifyConfig = spotifyConfig;
    }

    /**
     * ✔ Client Credentials 방식 토큰 발급
     */
    public String getAccessToken() {
        String clientId = spotifyConfig.getClientId();
        String clientSecret = spotifyConfig.getClientSecret();

        String auth = clientId + ":" + clientSecret;
        String encoded = Base64.getEncoder()
                .encodeToString(auth.getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Basic " + encoded);

        HttpEntity<String> request = new HttpEntity<>("grant_type=client_credentials", headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                "https://accounts.spotify.com/api/token",
                HttpMethod.POST,
                request,
                Map.class
        );

        return response.getBody().get("access_token").toString();
    }


    /**
     * ✔ 단일 트랙 조회
     */
    public SpotifyTrackInfoDto getTrackInfo(String trackId) {
        String accessToken = getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://api.spotify.com/v1/tracks/" + trackId,
                HttpMethod.GET,
                request,
                String.class
        );

        try {
            JsonNode root = objectMapper.readTree(response.getBody());

            String name = root.get("name").asText();
            String artistName = root.get("artists").get(0).get("name").asText();
            String albumName = root.get("album").get("name").asText();

            JsonNode images = root.get("album").get("images");
            String imageUrl = null;

            if (images != null && images.size() > 0) {
                imageUrl = images.get(0).get("url").asText();
            }

            return new SpotifyTrackInfoDto(trackId, name, artistName, albumName, imageUrl);

        } catch (Exception e) {
            return new SpotifyTrackInfoDto(trackId, "Unknown", "Unknown", "Unknown", null);
        }
    }
}
