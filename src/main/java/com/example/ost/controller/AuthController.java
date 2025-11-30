package com.example.ost.controller;

import com.example.ost.domain.user.User;
import com.example.ost.service.SpotifyAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final SpotifyAuthService authService;

    public AuthController(SpotifyAuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String login() {
        return authService.loginUrl();
    }

    @PostMapping("/callback")
    public Map<String, Object> callback(@RequestBody Map<String, String> body) {

        String code = body.get("code");
        User user = authService.handleCallback(code);

        Map<String, Object> result = new HashMap<>();
        result.put("nickname", user.getDisplayName());
        result.put("profileImage", user.getProfileImage());
        result.put("spotifyId", user.getSpotifyId());

        return result;
    }

}

