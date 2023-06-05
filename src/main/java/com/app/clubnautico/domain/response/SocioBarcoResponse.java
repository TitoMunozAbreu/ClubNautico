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
    private String nombre;
    private String apellidos;
    private Set<BarcoResponse> barcos;
}
