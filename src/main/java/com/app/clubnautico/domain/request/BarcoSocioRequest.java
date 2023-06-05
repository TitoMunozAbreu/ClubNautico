package com.app.clubnautico.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BarcoSocioRequest {
    private String numeroMatricula;
    private String nombre;
    private int numeroAmarre;
    private double cuotaPago;
    private int socioID;
}
