package com.app.clubnautico.domain.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroSalidaPatronRequest {
    @Schema(example = "dd-MM-yyyy HH:mm:ss")
    private String fechaSalida;
    private String destino;
    private Integer barcoID;
    private PatronRequest patron;
}
