package pe.edu.upc.TFFinanzas.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pe.edu.upc.TFFinanzas.dtos.UserDTO;
import pe.edu.upc.TFFinanzas.dtos.auth.AuthRequestDTO;
import pe.edu.upc.TFFinanzas.dtos.auth.RegisterRequestDTO;
import pe.edu.upc.TFFinanzas.dtos.auth.ResponseDTO;
import pe.edu.upc.TFFinanzas.entities.UserEntity;
import pe.edu.upc.TFFinanzas.services.AuthService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO request) {
        ResponseDTO response = authService.login(request);
        if(response.getMessage().equals("Credenciales incorrectas."))
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        else 
            return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO request) {
        ResponseDTO response = authService.register(request);
        if(response.getMessage().equals("El usuario ya existe."))
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        else 
            return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/buscar-por-username/{username}")
    public ResponseEntity<UserEntity> buscarUsuarioPorUsername(@PathVariable String username) {
        Optional<UserEntity> user = authService.findByUsername(username);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/users")
    public List<UserDTO> listAllUsers() {
        return authService.listAllUsers();
    }
}
