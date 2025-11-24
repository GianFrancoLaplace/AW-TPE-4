package org.example.microservicioidentidadyauth.service;

import org.example.microservicioidentidadyauth.entity.Rol;
import org.example.microservicioidentidadyauth.entity.Usuario;
import org.example.microservicioidentidadyauth.repository.UsuarioRepository;
import org.example.microservicioidentidadyauth.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UsuarioRepository authRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<Usuario> buscarPorEmail(String email) {
        return authRepository.findByEmail(email);
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return authRepository.findById(id);
    }

    public List<Usuario> buscarTodosLosUsuarios(){
        return authRepository.findAll();
    }

    public boolean validarToken(String token) {
        return jwtUtil.validateToken(token);
    }

    public Usuario registrarUsuario(Usuario u) {
        // Validaciones
        if (u.getEmail() == null || u.getEmail().trim().isEmpty()) {
            throw new RuntimeException("El email es obligatorio");
        }

        if (u.getContrasenia() == null || u.getContrasenia().length() < 6) {
            throw new RuntimeException("La contraseña debe tener al menos 6 caracteres");
        }

        if (authRepository.existsByEmail(u.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Encriptar contraseña
        u.setContrasenia(passwordEncoder.encode(u.getContrasenia()));
        u.setRol(Rol.USER);

        return authRepository.save(u);
    }

    public String login(String email, String password) {
        Optional<Usuario> usuarioOpt = authRepository.findByEmail(email);

        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        Usuario usuario = usuarioOpt.get();

        if (!passwordEncoder.matches(password, usuario.getContrasenia())) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        // Generar token con información del usuario
        return jwtUtil.generateToken(usuario);
    }
}