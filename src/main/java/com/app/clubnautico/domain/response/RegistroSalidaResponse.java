package com.app.clubnautico.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistroSalidaResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "ddMMyyy hh:mm:ss")
    private LocalDateTime fechaSalida;
    private String destino;
    private PatronResponse patron;
}
