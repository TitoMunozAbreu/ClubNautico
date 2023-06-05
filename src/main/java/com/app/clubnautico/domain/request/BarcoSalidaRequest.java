package com.app.clubnautico.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BarcoSalidaRequest {
    private String numeroMatricula;
    private String nombre;
    private int numeroAmarre;
    private double cuotaPago;
    private Set<RegistroSalidaSocioRequest> salidaRequestSet;
}
