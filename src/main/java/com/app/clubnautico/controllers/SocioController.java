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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
                    content = @Content),
            @ApiResponse(
                    description = "No autorizado / Token invalido",
                    responseCode = "403",
                    content = @Content
            )})
    @PostMapping
    public ResponseEntity<?> crearSocio(@RequestBody @Valid SocioBarcoRequest socioBarcoRequest,
                                        Authentication authentication){

        return this.socioService.crearSocio(socioBarcoRequest);
    }


    @Operation(summary = "Listar Socios",
            description = "Listado de socios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Lista de socios",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SocioResponse.class))}),
            @ApiResponse(responseCode = "404", description = "No existen lista de socios",
                    content = @Content),
            @ApiResponse(
                    description = "No autorizado / Token invalido",
                    responseCode = "403",
                    content = @Content
            )})
    @GetMapping
    public ResponseEntity<Page<SocioResponse>> listarSocio(@RequestParam Optional<String> nombre,
                                                           @RequestParam Optional<Integer> page,
                                                           @RequestParam Optional<Integer> size){
        return this.socioService.listarSocio(nombre.orElse(""),
                                            page.orElse(0),
                                            size.orElse(5));
    }

    @GetMapping("/existeDniNie")
    public ResponseEntity<?> existeSocioPorDocumentoIdentidad(@RequestParam String dniNie){
        return this.socioService.existeSocioPorDocumentoIdentidad(dniNie);
    }

    @GetMapping("/existeEmail")
    public ResponseEntity<?> existeSocioPorEmail(@RequestParam String email){
        return this.socioService.existeSocioPorEmail(email);
    }

    @Operation(summary = "Obtener Socio",
            description = "obtener datos del socio segun ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Socio encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SocioBarcoResponse.class))}),
            @ApiResponse(responseCode = "404", description = "No existe socio",
                    content = @Content),
            @ApiResponse(
                    description = "No autorizado / Token invalido",
                    responseCode = "403",
                    content = @Content
            )})
    @GetMapping("/{socioID}")
    public ResponseEntity<SocioBarcoResponse> obtenerSocioByID(@Parameter(description = "Insertar socioID")
                                                               @PathVariable Integer socioID){
       return this.socioService.obtenerSocioByID(socioID);
    }


    @Operation(summary = "Actualizar Socio",
            description = "Actualizar datos del socio segun ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Socio actualizado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SocioRequest.class))}),
            @ApiResponse(responseCode = "404", description = "No existe socio",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "datos ingresados invalido",
                    content = @Content),
            @ApiResponse(
                    description = "No autorizado / Token invalido",
                    responseCode = "403",
                    content = @Content
            )})
    @PutMapping("/{socioID}")
    public ResponseEntity<?> actualizarSociobyID(@Parameter(description = "Insertar socioID")
                                                @PathVariable Integer socioID,
                                                 @RequestBody @Valid SocioRequest socioRequest){
        return this.socioService.actualizarSociobyID(socioID, socioRequest);
    }


    @Operation(summary = "Eliminar Socio",
            description = "Eliminar socio segun ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Socio eliminado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "No existe socio",
                    content = @Content),
            @ApiResponse(
                    description = "No autorizado / Token invalido",
                    responseCode = "403",
                    content = @Content
            )})
    @DeleteMapping("/{socioID}")
    public ResponseEntity<?> eliminarSocioByID(@Parameter(description = "Insertar socioID")
                                               @PathVariable Integer socioID){
        return this.socioService.eliminarSocioByID(socioID);
    }


    @Operation(summary = "Registrar salida con Socio",
            description = "detallar salida con socio como patron")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Salida registrada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegistroSalidaSocioRequest.class))}),
            @ApiResponse(responseCode = "404", description = "datos ingresados invalido",
                    content = @Content),
            @ApiResponse(
                    description = "No autorizado / Token invalido",
                    responseCode = "403",
                    content = @Content
            )})
    @PostMapping("/{socioID}/barcos/{barcoID}/salidas")
    public ResponseEntity<?> registrarSalidaSocioByID(@RequestBody @Valid RegistroSalidaSocioRequest salidaRequest){
        return this.socioService.registrarSalidaSocioByID(salidaRequest);

    }



}
