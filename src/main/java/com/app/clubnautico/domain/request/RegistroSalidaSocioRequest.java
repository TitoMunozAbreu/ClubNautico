package com.app.clubnautico.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroSalidaSocioRequest {
    @Schema(example = "dd-MM-yyyy HH:mm:ss")
    private String fechaSalida;
    private String destino;
    private Integer patronID;
    private Integer barcoID;

}
