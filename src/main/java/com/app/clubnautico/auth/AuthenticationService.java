package com.app.clubnautico.auth;

import com.app.clubnautico.security.config.JwtService;
import com.app.clubnautico.security.user.Role;
import com.app.clubnautico.security.user.User;
import com.app.clubnautico.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<AuthenticationResponse> register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole().toUpperCase()))
                .build();

        repository.save(user);

        var jwtToken = jwtService.generateToken(user);

        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .token(jwtToken)
                .role(user.getRole().name())
                .expiresIn(LocalDateTime.now().plusHours(1))
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authenticationResponse);
    }

    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );

        if(!authenticate.isAuthenticated()){
            throw new BadCredentialsException("Usuario y contraseña inválidos");
        }

        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);

        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .token(jwtToken)
                .role(user.getRole().name())
                .expiresIn(LocalDateTime.now().plusHours(1))
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .body(authenticationResponse);


    }
}
