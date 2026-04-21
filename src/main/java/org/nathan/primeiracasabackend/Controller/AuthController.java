package org.nathan.primeiracasabackend.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.nathan.primeiracasabackend.Model.Usuario;
import org.nathan.primeiracasabackend.Repository.UsuarioRepository;
import org.nathan.primeiracasabackend.Service.JwtService;
import org.nathan.primeiracasabackend.dto.request.LoginRequestDTO;
import org.nathan.primeiracasabackend.dto.request.UsuarioRequestDTO;
import org.nathan.primeiracasabackend.dto.response.LoginResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository  usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequest) {

        // tenta autenticar o usuário com e-mail e senha
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha())
        );

        // se tiver sucesso, gera o token JWT
        String token = jwtService.gerarToken(authentication.getName());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UsuarioRequestDTO dto) {
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(dto.getNome());
        novoUsuario.setEmail(dto.getEmail());

        // criptografa a senha antes de salvar
        novoUsuario.setSenha(new BCryptPasswordEncoder().encode(dto.getSenha()));

        usuarioRepository.save(novoUsuario);
        return ResponseEntity.ok("Usuário registrado com sucesso!");
    }
}