package org.example.microservicioidentidadyauth.controller;
import org.example.microservicioidentidadyauth.entity.Usuario;
import org.example.microservicioidentidadyauth.service.AuthService;
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
    public ResponseEntity<?> register(@RequestBody Usuario usuario) {
        try {
            Usuario user = authService.registrarUsuario(usuario);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // 🔹 Login de usuario
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String password = request.get("password");
            String token = authService.login(email, password);

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "email", email
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }
    }

}
