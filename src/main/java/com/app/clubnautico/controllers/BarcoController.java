package com.app.clubnautico.controllers;

import com.app.clubnautico.domain.Socio;
import com.app.clubnautico.domain.request.*;
import com.app.clubnautico.domain.response.BarcoResponse;
import com.app.clubnautico.domain.response.BarcoSalidaResponse;
import com.app.clubnautico.domain.response.SocioBarcoResponse;
import com.app.clubnautico.domain.response.SocioResponse;
import com.app.clubnautico.services.BarcoService;
import com.app.clubnautico.services.RegistroSalidaService;
import com.app.clubnautico.services.SocioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/barcos")
@Tag(name = "Barco", description = "controlador con metodos relacionados al barco")
public class BarcoController {

    @Autowired
    private BarcoService barcoService;
    @Autowired
    private SocioService socioService;

    @Autowired
    private RegistroSalidaService salidaService;


    @Operation(summary = "Crear Barco",
            description = "Crear barco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Barco creado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BarcoSocioRequest.class))}),
            @ApiResponse(responseCode = "400", description = "No Barco creado",
                    content = @Content),
            @ApiResponse(
                    description = "No autorizado / Token invalido",
                    responseCode = "403",
                    content = @Content
            )})
    @PostMapping
    public ResponseEntity<?> crearBarco(@RequestBody @Valid BarcoSocioRequest barcoSocioRequest) {
        Integer requestSocioID = barcoSocioRequest.getSocioID();
        //almacenar socioFound de BBDD
        Socio socioFound = this.socioService.obtenerSocio(requestSocioID);

        return this.barcoService.crearBarco(barcoSocioRequest, socioFound);
    }


    @Operation(summary = "Listar Barcos",
            description = "Listado de barcos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de barcos",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BarcoResponse.class))}),
            @ApiResponse(responseCode = "404", description = "No existen lista de barcos",
                    content = @Content),
            @ApiResponse(
                    description = "No autorizado / Token invalido",
                    responseCode = "403",
                    content = @Content
            )})
    @GetMapping
    public ResponseEntity<List<BarcoResponse>> listarBarcos() {
        return this.barcoService.listarBarcos();
    }


    @Operation(summary = "Obtener Barco",
            description = "obtener datos del barco segun ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Barco encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BarcoSalidaResponse.class))}),
            @ApiResponse(responseCode = "404", description = "No existe barco",
                    content = @Content),
            @ApiResponse(
                    description = "No autorizado / Token invalido",
                    responseCode = "403",
                    content = @Content
            )})
    @GetMapping("/{barcoID}")
    public ResponseEntity<BarcoSalidaResponse> obtenerBarcoSegunID(@Parameter(description = "Insertar barcoID")
                                                                   @PathVariable Integer barcoID) {
        return this.barcoService.obtenerBarcoSegunID(barcoID);

    }


    @Operation(summary = "Registrar salida con patron",
            description = "detallar salida con patron")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Salida registrada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BarcoRequest.class))}),
            @ApiResponse(responseCode = "404", description = "datos ingresados invalido",
                    content = @Content),
            @ApiResponse(
                    description = "No autorizado / Token invalido",
                    responseCode = "403",
                    content = @Content
            )})
    @PostMapping("/salidas")
    public ResponseEntity<?> crearSalidaSegunBarcoID(@RequestBody RegistroSalidaPatronRequest salidaPatronRequest) {
        return this.barcoService.crearSalidaSegunBarcoID(salidaPatronRequest);
    }


    @Operation(summary = "Actualizar Barco",
            description = "Actualizar datos del barco segun ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Barco actualizado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BarcoRequest.class))}),
            @ApiResponse(responseCode = "404", description = "No existe barco",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "datos ingresados invalido",
                    content = @Content),
            @ApiResponse(
                    description = "No autorizado / Token invalido",
                    responseCode = "403",
                    content = @Content
            )})
    @PutMapping("/{barcoID}")
    public ResponseEntity<?> actualizarBarcoSegunID(@Parameter(description = "Insertar barcoID")
                                                    @PathVariable Integer barcoID,
                                                    @RequestBody @Valid BarcoRequest barcoRequest){
        return this.barcoService.actualizarBarcoSegunID(barcoID, barcoRequest);
    }


    @Operation(summary = "Eliminar Barco",
            description = "Eliminar barco segun ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Barco eliminado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "No existe barco",
                    content = @Content),
            @ApiResponse(
                    description = "No autorizado / Token invalido",
                    responseCode = "403",
                    content = @Content
            )})
    @DeleteMapping("/{barcoID}")
    public ResponseEntity<?> eliminarBarcoSegunID(@Parameter(description = "Insertar barcoID")
                                                      @PathVariable Integer barcoID){
        return this.barcoService.eliminarBarcoSegunID(barcoID);
    }


    @Operation(summary = "Eliminar Salida",
            description = "Eliminar salida segun ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Salida eliminada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "No existe salida",
                    content = @Content),
            @ApiResponse(
                    description = "No autorizado / Token invalido",
                    responseCode = "403",
                    content = @Content
            )})
    @DeleteMapping("/salidas/{salidaID}")
    public ResponseEntity<?> eliminarSalidaSegunID(@Parameter(description = "Insertar salidaID")
                                                   @PathVariable Integer salidaID){

        return this.salidaService.eliminarSalidaSegunID(salidaID);
    }

}
