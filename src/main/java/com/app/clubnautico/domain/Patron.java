package com.app.clubnautico.domain;

import com.app.clubnautico.domain.custom_validation.ValidacionIdentidad;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name ="patron",
        uniqueConstraints = {@UniqueConstraint(name = "documento_identidad",
                columnNames = "documento_identidad")})
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Patron extends Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patron_id")
    private Integer patronId;

    @Column(name = "documento_identidad",
            length = 9)
    @NotBlank(message = "debes ingresar un DNI o NIE")
    @ValidacionIdentidad
    private String documentoIdentidad;

    @OneToMany(mappedBy = "patron",
                orphanRemoval = true)
    private Set<RegistroSalida> registroSalidas;
}
