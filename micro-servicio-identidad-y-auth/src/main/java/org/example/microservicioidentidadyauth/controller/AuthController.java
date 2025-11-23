package org.example.microservicioidentidadyauth.controller;
import org.example.microservicioidentidadyauth.dto.LoginRequestDTO;
import org.example.microservicioidentidadyauth.dto.LoginResponseDTO;
import org.example.microservicioidentidadyauth.dto.RegisterDTO;
import org.example.microservicioidentidadyauth.entity.Usuario;
import org.example.microservicioidentidadyauth.service.AuthService;
import org.example.microservicioidentidadyauth.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping()
    public ResponseEntity<List<Usuario>> obtenerUsuarios(){
        List<Usuario> usuarios = authService.buscarTodosLosUsuarios();
        if(usuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Usuario>> obtenerUsuario(@PathVariable Long id){
        Optional<Usuario> usuario = authService.buscarPorId(id);
        if(usuario.isPresent()){
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO request) {
        try {
            Usuario usuario = new Usuario();
            usuario.setNombre(request.getNombre());
            usuario.setApellido(request.getApellido());
            usuario.setEmail(request.getEmail());
            usuario.setContrasenia(request.getContrasenia());
            usuario.setTelefono(request.getTelefono());

            Usuario user = authService.registrarUsuario(usuario);

            // No devolver la contraseña
            user.setContrasenia(null);

            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        try {
            String token = authService.login(request.getEmail(), request.getContrasenia());

            Optional<Usuario> usuarioOpt = authService.buscarPorEmail(request.getEmail());

            if (usuarioOpt.isPresent()) {
                Usuario usuario = usuarioOpt.get();

                return ResponseEntity.ok(new LoginResponseDTO(
                        token,
                        usuario.getId(),
                        usuario.getEmail(),
                        usuario.getNombre()
                ));
            }

            return ResponseEntity.ok(Map.of("token", token));

        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401)
                        .body(Map.of("valid", false, "error", "Token no proporcionado"));
            }

            String token = authHeader.substring(7);

            if (!jwtUtil.validateToken(token)) {
                return ResponseEntity.status(401)
                        .body(Map.of("valid", false, "error", "Token inválido o expirado"));
            }

            // Extraer información del token
            String email = jwtUtil.getEmailFromToken(token);
            Long userId = jwtUtil.getUserIdFromToken(token);

            return ResponseEntity.ok(Map.of(
                    "valid", true,
                    "email", email,
                    "userId", userId
            ));

        } catch (Exception e) {
            return ResponseEntity.status(401)
                    .body(Map.of("valid", false, "error", e.getMessage()));
        }
    }

    // Endpoint para obtener info del usuario actual (desde el token)
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String email = jwtUtil.getEmailFromToken(token);

            Optional<Usuario> usuario = authService.buscarPorEmail(email);
            if (usuario.isPresent()) {
                // No devolver la contraseña
                Usuario user = usuario.get();
                user.setContrasenia(null);
                return ResponseEntity.ok(user);
            }

            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", "Token inválido"));
        }
    }
}
