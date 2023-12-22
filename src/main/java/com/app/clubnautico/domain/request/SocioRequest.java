package com.app.clubnautico.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocioRequest {
    private String nombre;
    private String apellidos;
    private String movil;
    private String email;
}
