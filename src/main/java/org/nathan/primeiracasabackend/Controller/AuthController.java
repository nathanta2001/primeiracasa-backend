package org.nathan.primeiracasabackend.Controller;

import lombok.RequiredArgsConstructor;
import org.nathan.primeiracasabackend.Service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> creds) {

        if ("admin".equals(creds.get("username")) &&
                "admin".equals(creds.get("password"))) {

            String token = jwtService.gerarToken("admin");

            return ResponseEntity.ok(Map.of("token", token));
        }

        return ResponseEntity.status(401).body("Credenciais inválidas");
    }
}