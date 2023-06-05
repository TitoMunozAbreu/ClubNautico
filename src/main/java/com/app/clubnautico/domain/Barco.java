package com.app.clubnautico.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "barco",
        uniqueConstraints = {@UniqueConstraint(name = "numero_amarre",
                columnNames = "numero_amarre")})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Barco implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "barco_id")
    private Integer barcoID;

    @NotBlank(message = "debes registrar un nombre de matricula")
    @Column(name = "numero_matricula")
    private String numeroMatricula;

    @NotBlank(message = "debes registrar un nombre al barco")
    @Pattern(regexp = "^[^<>]*$", message = "No puedes ingresar caracteres invalidos: '< | >")
    @Column(length = 30)
    private String nombre;

    @NotNull(message = "debes registrar el numero de amarre")
    @Column(name = "numero_amarre",
            columnDefinition = "TINYINT(2) UNSIGNED")
    @Digits(integer = 2,fraction = 0, message = "debes ingresar un numero de Amarre maximo 2 digitos")
    @Positive(message = "Debes ingresar un numero de amarre positivo-entero")
    private int numeroAmarre;

    @NotNull(message = "debes registrar la cuota de pago")
    @Column(name = "cuota_pago",
            columnDefinition = "DOUBLE(5,2)")
    @Positive(message = "La cuota de pago no puede ser un monto negativo")
    private double cuotaPago;

    @OneToMany(mappedBy = "barco",
                orphanRemoval = true)
    private Set<RegistroSalida> registroSalidas;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "socio_id",
                foreignKey = @ForeignKey(name = "FK_socio_id"))
    private Socio socio;
}
