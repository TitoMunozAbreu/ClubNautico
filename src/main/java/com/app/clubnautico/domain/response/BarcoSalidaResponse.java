package com.app.clubnautico.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BarcoSalidaResponse {
    private String numeroMatricula;
    private String nombre;
    private int numeroAmarre;
    private double cuotaPago;
    private Set<RegistroSalidaResponse> salidaResponseSet;
}
