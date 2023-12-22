package com.app.clubnautico.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationRequest {

    @NotBlank(message = "debes registrar un email")
    @Email(message = "debes registrar un email")
    @Pattern(regexp = ".*@club-nautico\\.com",message = "dominio valido para el email: example@club-nautico.com")
    private String email;

    @Pattern(regexp = "^[^<>]+$", message = "No puedes ingresar caracteres invalidos: '< | >")
    private String password;
}
