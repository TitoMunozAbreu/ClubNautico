package com.app.clubnautico.auth;

import com.app.clubnautico.domain.request.SocioBarcoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "controlador para autenticacion - registro de usuario")
public class AuthenticationController {

    private final AuthenticationService service;

    @Operation(summary = "Registrar Usuario",
            description = "Registrar Usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario registrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticationResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Usuario no registrado",
                    content = @Content)})
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest request) {
        return  service.register(request);
    }


    @Operation(summary = "Autenticar Usuario",
            description = "Autenticar Usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario auntenticado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticationResponse.class))}),
            @ApiResponse(
                    responseCode = "401",
                    description = "Acceso invalido: autenticacion invalida",
                    content = @Content)})
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        return service.authenticate(request);
    }
}
