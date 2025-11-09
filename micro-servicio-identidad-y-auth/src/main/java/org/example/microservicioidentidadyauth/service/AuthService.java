package org.example.microservicioidentidadyauth.service;

import lombok.Data;
import org.example.microservicioidentidadyauth.entity.Usuario;
import org.example.microservicioidentidadyauth.repository.UsuarioRepository;
import org.example.microservicioidentidadyauth.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class AuthService {
    @Autowired
    private UsuarioRepository authRepository;
    private JwtUtil jwtUtil;
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
        try {
            jwtUtil.extractAllClaims(token);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    //Registro de usuario
    public Usuario registrarUsuario(Usuario u) {
        if (authRepository.existsByEmail(u.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        // encriptamos la contraseña antes de guardarla
        u.setContrasenia(passwordEncoder.encode(u.getContrasenia()));
//        u.setRol("USER");
        return authRepository.save(u);
    }

    //Login (autenticación)
    public String login(String email, String password) {
        Optional<Usuario> usuarioOpt = authRepository.findByEmail(email);

        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        Usuario usuario = usuarioOpt.get();

        //comparamos la contraseña encriptada
        if (!passwordEncoder.matches(password, usuario.getContrasenia())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        //si todo ok, generamos un token JWT
        return jwtUtil.generateToken(usuario.getEmail());
    }
}
