package com.app.clubnautico.controllers;

import com.app.clubnautico.domain.request.RegistroSalidaSocioRequest;
import com.app.clubnautico.domain.request.SocioBarcoRequest;
import com.app.clubnautico.domain.request.SocioRequest;
import com.app.clubnautico.domain.response.SocioBarcoResponse;
import com.app.clubnautico.domain.response.SocioResponse;
import com.app.clubnautico.services.SocioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/socios")
@Tag(name = "Socio", description = "controlador con metodos relacionados al socio")
public class SocioController {
    private SocioService socioService;

    @Autowired
    public SocioController(SocioService socioService){
        this.socioService = socioService;
    }


    @Operation(summary = "Crear Socio",
            description = "posibilidad de crear socio con ó sin barco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Socio creado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SocioBarcoRequest.class))}),
            @ApiResponse(responseCode = "400", description = "No socio creado",
                    content = @Content)})
    @PostMapping
    public ResponseEntity<?> crearSocio(@RequestBody @Valid SocioBarcoRequest socioBarcoRequest){
        return this.socioService.crearSocio(socioBarcoRequest);
    }


    @Operation(summary = "Listar Socios",
            description = "Listado de socios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de socios",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SocioResponse.class))}),
            @ApiResponse(responseCode = "404", description = "No existen lista de socios",
                    content = @Content)})
    @GetMapping
    public ResponseEntity<List<SocioResponse>> listarSocio(){
        return this.socioService.listarSocio();
    }


    @Operation(summary = "Obtener Socio",
            description = "obtener datos del socio segun ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Socio encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SocioBarcoResponse.class))}),
            @ApiResponse(responseCode = "404", description = "No existe socio",
                    content = @Content)})
    @GetMapping("/{socioID}")
    public ResponseEntity<SocioBarcoResponse> obtenerSocioByID(@Parameter(description = "Insertar socioID")
                                                               @PathVariable Integer socioID){
       return this.socioService.obtenerSocioByID(socioID);
    }


    @Operation(summary = "Actualizar Socio",
            description = "Actualizar datos del socio segun ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Socio actualizado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SocioRequest.class))}),
            @ApiResponse(responseCode = "404", description = "No existe socio",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "datos ingresados invalido",
                    content = @Content)})
    @PutMapping("/{socioID}")
    public ResponseEntity<?> actualizarSociobyID(@Parameter(description = "Insertar socioID")
                                                @PathVariable Integer socioID,
                                                 @RequestBody @Valid SocioRequest socioRequest){
        return this.socioService.actualizarSociobyID(socioID, socioRequest);
    }


    @Operation(summary = "Eliminar Socio",
            description = "Eliminar socio segun ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Socio eliminado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "No existe socio",
                    content = @Content)})
    @DeleteMapping("/{socioID}")
    public ResponseEntity<?> eliminarSocioByID(@Parameter(description = "Insertar socioID")
                                               @PathVariable Integer socioID){
        return this.socioService.eliminarSocioByID(socioID);
    }


    @Operation(summary = "Registrar salida con Socio",
            description = "detallar salida con socio como patron")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Salida registrada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegistroSalidaSocioRequest.class))}),
            @ApiResponse(responseCode = "404", description = "datos ingresados invalido",
                    content = @Content)})
    @PostMapping("/{socioID}/barcos/{barcoID}/salidas")
    public ResponseEntity<?> registrarSalidaSocioByID(@RequestBody @Valid RegistroSalidaSocioRequest salidaRequest){
        return this.socioService.registrarSalidaSocioByID(salidaRequest);

    }

}
