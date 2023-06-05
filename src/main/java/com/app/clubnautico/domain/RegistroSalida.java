package com.app.clubnautico.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "registro_salida")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistroSalida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "salida_id")
    private Integer salidaId;

    @NotNull(message = "debes registrar una fecha")
    @PastOrPresent(message = "registra una fecha valida, de hoy o dias anteriores")
    @Column(name = "fecha_salida",
            columnDefinition = "DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "ddMMyyy hh:mm:ss")
    private LocalDateTime fechaSalida;

    @NotBlank(message = "debes registrar un destino")
    @Pattern(regexp = "^[^<>]*$", message = "No puedes ingresar caracteres invalidos: '< | >")
    @Column(length = 20)
    private String destino;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "barco_id",
                foreignKey = @ForeignKey(name = "FK_barco_id"))
    private Barco barco;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "persona_id",
            foreignKey = @ForeignKey(name = "FK_persona_id"))
    private Patron patron;
}
