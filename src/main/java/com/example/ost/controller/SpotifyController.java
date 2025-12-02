package com.example.ost.controller;

import com.example.ost.service.SpotifyApiService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/spotify")
public class SpotifyController {

    private final SpotifyApiService api;

    public SpotifyController(SpotifyApiService api) {
        this.api = api;
    }

    @GetMapping("/search")
    public String search(@RequestParam String q) {
        return api.search(q);
    }

    @GetMapping("/top-tracks")
    public Object getTopTracks() {
        return api.getTopTracks();
    }

    @GetMapping("/preview")
    public String getPreview(@RequestParam String trackId) {
        return api.getPreviewUrl(trackId);
    }
    @GetMapping("/track/{trackId}")
    public Object getTrackDetail(@PathVariable String trackId) {
        return api.getTrackDetail(trackId);
    }


}
