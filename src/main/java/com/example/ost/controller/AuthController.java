package com.example.ost.controller;

import com.example.ost.service.SpotifyAuthService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
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
        return authService.loginUrl(); // 프론트에서 직접 요청하면 URL만 줌
    }

    @PostMapping("/callback")
    public ResponseEntity<String> callback(@RequestBody Map<String, String> body) {
        String code = body.get("code");
        authService.requestToken(code);
        return ResponseEntity.ok("success");
    }
}

