package org.nathan.primeiracasabackend.Service;

import lombok.RequiredArgsConstructor;
import org.nathan.primeiracasabackend.Exception.ResourceNotFoundException;
import org.nathan.primeiracasabackend.Model.Usuario;
import org.nathan.primeiracasabackend.Repository.UsuarioRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;

    public Usuario getUsuarioLogado() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            throw new AccessDeniedException("Falha na autenticação: Usuário não logado");
        }

        return usuarioRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }
}