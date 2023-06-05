package com.app.clubnautico.domain;

import com.app.clubnautico.domain.custom_validation.ValidacionIdentidad;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
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
@MappedSuperclass
public abstract class Persona {

    @NotBlank(message = "debes registrar un nombre")
    @Pattern(regexp = "^[^<>]+$", message = "No puedes ingresar caracteres invalidos: '< | >")
    @Column(length = 30)
    private String nombre;

    @NotBlank(message = "debes registrar un apellido")
    @Pattern(regexp = "^[^<>]+$", message = "No puedes ingresar caracteres invalidos: '< | >")
    @Column(length = 30)
    private String apellidos;

    @NotBlank(message = "debes registrar un movil")
    @Digits(integer = 9,fraction = 0, message = "debes registrar un movil de 9 digitos")
    @Column(length = 9)
    private String movil;

    @NotBlank(message = "debes registrar un email")
    @Email(message = "debes registrar un email")
    @Pattern(regexp = ".*@club-nautico\\.com",message = "dominio valido para el email: example@club-nautico.com")
    private String email;


}
