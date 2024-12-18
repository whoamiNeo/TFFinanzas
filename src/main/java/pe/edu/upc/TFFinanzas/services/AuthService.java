package pe.edu.upc.TFFinanzas.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pe.edu.upc.TFFinanzas.dtos.UserDTO;
import pe.edu.upc.TFFinanzas.dtos.auth.AuthRequestDTO;
import pe.edu.upc.TFFinanzas.dtos.auth.AuthResponseDTO;
import pe.edu.upc.TFFinanzas.dtos.auth.RegisterRequestDTO;
import pe.edu.upc.TFFinanzas.dtos.auth.ResponseDTO;
import pe.edu.upc.TFFinanzas.entities.RoleEnum;
import pe.edu.upc.TFFinanzas.entities.UserEntity;
import pe.edu.upc.TFFinanzas.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthService {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private JwtService jwtService;
    private PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository,
            JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseDTO login(AuthRequestDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            UserDetails userDetails = userRepository.findByUsername(request.getUsername()).get();
            String token = jwtService.genToken(userDetails);
            return new AuthResponseDTO("Inicio de sesion exitoso.", token);
        } catch (AuthenticationException ex) {
            return new ResponseDTO("Credenciales incorrectas.");
        }
    }

    public ResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent())
            return new ResponseDTO("El usuario ya existe.");
        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() == null ? RoleEnum.USER : request.getRole())
                .build();
        userRepository.save(user);
        String token = jwtService.genToken(user);
        return new AuthResponseDTO("Usuario creado correctamente.", token);
    }

    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<UserDTO> listAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private UserDTO convertToDTO(UserEntity user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());
        return userDTO;
    }
}
