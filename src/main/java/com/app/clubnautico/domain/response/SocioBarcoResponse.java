package com.app.clubnautico.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocioBarcoResponse {
    private Integer id;
    private String nombre;
    private String apellidos;
    private String movil;
    private String email;
    private Set<BarcoResponse> barcos;
}
