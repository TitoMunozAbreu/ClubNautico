package com.app.clubnautico.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatronRequest {
    private String documentoIdentidad;
    private String nombre;
    private String apellidos;
    private String movil;
    private String email;
  }
