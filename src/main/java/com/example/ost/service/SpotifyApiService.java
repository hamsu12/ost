package com.example.ost.service;

import com.example.ost.dto.SpotifyTrackInfoDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpotifyApiService {

    private final SpotifyApiClient apiClient;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SpotifyApiService(SpotifyApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public String search(String q) {

        String accessToken = apiClient.getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        String url = "https://api.spotify.com/v1/search?q=" + q + "&type=track&limit=49";

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        ).getBody();
    }

    public List<SpotifyTrackInfoDto> getTopTracks() {

        String accessToken = apiClient.getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            String url =
                    "https://api.spotify.com/v1/search" +
                            "?q=year:2024" +
                            "&type=track" +
                            "&limit=20" +
                            "&market=KR";

            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    String.class
            );

            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode items = root.get("tracks").get("items");

            List<SpotifyTrackInfoDto> result = new ArrayList<>();

            for (JsonNode track : items) {
                if (track == null || track.isNull()) continue;

                String id = track.get("id").asText();
                String name = track.get("name").asText();

                String artistName = track.get("artists").get(0).get("name").asText();
                String albumName = track.get("album").get("name").asText();

                JsonNode images = track.get("album").get("images");
                String imageUrl = (images != null && images.size() > 0)
                        ? images.get(0).get("url").asText()
                        : null;

                result.add(new SpotifyTrackInfoDto(id, name, artistName, albumName, imageUrl));
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public String getPreviewUrl(String trackId) {

        String accessToken = apiClient.getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        String url = "https://api.spotify.com/v1/tracks/" + trackId;

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    String.class
            );

            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode preview = root.get("preview_url");


            if (preview == null || preview.isNull()) {
                return null;
            }

            return preview.asText();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
