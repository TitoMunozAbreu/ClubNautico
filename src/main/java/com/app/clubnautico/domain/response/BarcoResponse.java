package com.app.clubnautico.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BarcoResponse {
    private Integer id;
    private String nombre;
    private String numeroMatricula;
    private int numeroAmarre;
    private double cuotaPago;
}
