package com.app.clubnautico.services;

import com.app.clubnautico.domain.Barco;
import com.app.clubnautico.domain.Socio;
import com.app.clubnautico.domain.request.BarcoRequest;
import com.app.clubnautico.domain.request.BarcoSocioRequest;
import com.app.clubnautico.domain.request.RegistroSalidaPatronRequest;
import com.app.clubnautico.domain.response.BarcoResponse;
import com.app.clubnautico.domain.response.BarcoSalidaResponse;
import com.app.clubnautico.domain.response.RegistroSalidaResponse;
import com.app.clubnautico.repositories.BarcoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BarcoService {
    private BarcoRepository barcoRepository;
    private RegistroSalidaService salidaService;

    @Autowired
    public BarcoService(BarcoRepository barcoRepository,
                        RegistroSalidaService salidaService) {
        this.barcoRepository = barcoRepository;
        this.salidaService = salidaService;

    }

    @Transactional
    public Barco crearBarco(BarcoRequest barcoRequest, Socio newSocio) {
        //instanciar newBarco
        Barco newBarco = Barco.builder()
                //insertar datos a newBarco
                .socio(newSocio)
                .numeroMatricula(barcoRequest.getNumeroMatricula())
                .numeroAmarre(barcoRequest.getNumeroAmarre())
                .nombre(barcoRequest.getNombre())
                .cuotaPago(barcoRequest.getCuotaPago())
                .build();
        //persistir newBarco
        return this.barcoRepository.save(newBarco);
    }

    @Transactional
    public ResponseEntity<?> crearBarco(BarcoSocioRequest barcoSocioRequest, Socio newSocio) {
        //instanciar newBarco
        Barco newBarco = Barco.builder()
                //insertar datos a newBarco
                .socio(newSocio)
                .numeroMatricula(barcoSocioRequest.getNumeroMatricula())
                .numeroAmarre(barcoSocioRequest.getNumeroAmarre())
                .nombre(barcoSocioRequest.getNombre())
                .cuotaPago(barcoSocioRequest.getCuotaPago())
                .build();
        //persistir newBarco
        this.barcoRepository.save(newBarco);

        //personalizar header
        HttpHeaders headers = new HttpHeaders();
        headers.add("custom-header", "Barco creado");

        //devolver respuesta
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("{\"mensaje\":\" " +"Barco: " + barcoSocioRequest.getNombre() + " creado con exito.\"}");
    }

    @Transactional
    public ResponseEntity<List<BarcoResponse>> listarBarcos() {

        List<Barco> barcosFound = this.barcoRepository.findAll();

        if(barcosFound.isEmpty()){
            //lanzar excepcion si esta vacia la lista
            throw new NoSuchElementException("No existen barcos creados");
        }

        //mapear lista barcosFound a BarcoResponse
        List<BarcoResponse> barcoResponseList = barcosFound.stream()
                .map(barco -> {
                    //cargar los datos del barco al barcoResponse
                    BarcoResponse barcoResponse = BarcoResponse.builder()
                            .nombre(barco.getNombre())
                            .numeroAmarre(barco.getNumeroAmarre())
                            .cuotaPago(barco.getCuotaPago())
                            .build();
                    return barcoResponse;
                }).collect(Collectors.toList());

        //personalizar header
        HttpHeaders headers = new HttpHeaders();
        headers.add("custom-header", "Lista de barcos");

        //delvolver el lista de socios
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .headers(headers)
                .body(barcoResponseList);

    }

    @Transactional
    public Set<BarcoResponse> listarBarcosSegunSocioID(Integer socioID) {
        //mapear barcos del socioFound a BarcoResponse
        Set<BarcoResponse> barcoResponseSet = this.barcoRepository.listarBarcosBySocioID(socioID).stream()
                .map(barco -> {
                    BarcoResponse barcoResponse = BarcoResponse.builder()
                            .id(barco.getBarcoID())
                            .numeroMatricula(barco.getNumeroMatricula())
                            .cuotaPago(barco.getCuotaPago())
                            .numeroAmarre(barco.getNumeroAmarre())
                            .nombre(barco.getNombre())
                            .build();
                    return barcoResponse;
                }).collect(Collectors.toSet());

        return barcoResponseSet;
    }

    @Transactional
    public ResponseEntity<BarcoSalidaResponse> obtenerBarcoSegunID(Integer barcoID) {

        Barco barcoFound = this.barcoRepository.findById(barcoID)
                .orElseThrow(() -> new NoSuchElementException("No existe Barco ID: " + barcoID));

        Set<RegistroSalidaResponse> registroSalidaResponses = this.salidaService.listarSalidasSegunBarcoID(barcoID);

        //mapear barcoFound a BarcoSalidaResponse
        BarcoSalidaResponse barcoSalidaResponse = BarcoSalidaResponse.builder()
                .numeroMatricula(barcoFound.getNumeroMatricula())
                .nombre(barcoFound.getNombre())
                .numeroAmarre(barcoFound.getNumeroAmarre())
                .cuotaPago(barcoFound.getCuotaPago())
                .salidaResponseSet(registroSalidaResponses)
                .build();

        boolean listaSalidaVacia = registroSalidaResponses.isEmpty();

        if (!listaSalidaVacia) {
            //almacenar las lista de barcos del socio
            barcoSalidaResponse.setSalidaResponseSet(registroSalidaResponses);
        }
        //personalizar header
        HttpHeaders headers = new HttpHeaders();
        headers.add("custom-header", "obtener barco por ID");

        //devolver el socio
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .headers(headers)
                .body(barcoSalidaResponse);
    }
    @Transactional
    public Barco obtenerBarcoSegunSocioID(Integer socioID, Integer barcoID) {
        //devolver el barco encontrado en la BBDD
        return this.barcoRepository.obtenerBarcoBySocioID(barcoID)
                .orElseThrow(() -> new NoSuchElementException("No existe barco ID: " + barcoID));
    }

    @Transactional
    public void eliminarBarcoSegunSocioID(Integer socioID) {
        //alamacenar lista de barcos segun el socioID
        List<Barco> barcosFound = this.barcoRepository.findAll().stream()
                .filter(barco -> barco.getSocio().getSocioId() == socioID)
                .collect(Collectors.toList());

        //comprobar que barcosFound no este vacia
        if(!barcosFound.isEmpty()){
            //iterar sobre la lista barcosFound
            barcosFound.stream()
                    .forEach(barco -> {
                        //comprobar que existan registro de salida en cada barco
                        this.salidaService.eliminarSalidaSegunBarcoID(barco.getBarcoID());
                        //eliminar cada barco que contenga la lista
                        this.barcoRepository.delete(barco);});
        }

    }

    @Transactional
    public ResponseEntity<?> actualizarBarcoSegunID(Integer barcoID, BarcoRequest barcoRequest) {
        //almacenar el barcoFound
        Barco barcoFound = this.barcoRepository.findById(barcoID)
                .orElseThrow(() -> new NoSuchElementException("No existe barco ID: " + barcoID));

        //actualizar datos del barcoFound
        barcoFound.setNumeroMatricula(barcoRequest.getNumeroMatricula());
        barcoFound.setNombre(barcoRequest.getNombre());
        barcoFound.setNumeroAmarre(barcoRequest.getNumeroAmarre());
        barcoFound.setCuotaPago(barcoRequest.getCuotaPago());

        //persistir datos
        this.barcoRepository.save(barcoFound);

        //personalizar header
        HttpHeaders headers = new HttpHeaders();
        headers.add("custom-header", "Barco actualizado");

        //devolver respuesta
        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body(Map.of("mensaje", "Socio: " + barcoFound.getNombre() + " actualizado."));


    }

    @Transactional
    public ResponseEntity<?> crearSalidaSegunBarcoID(RegistroSalidaPatronRequest salidaPatronRequest) {
        //almancenar el barcoFound
        Barco barcoFound = this.barcoRepository.findById(salidaPatronRequest.getBarcoID())
                .orElseThrow(() -> new NoSuchElementException("No existe barco ID: " + salidaPatronRequest.getBarcoID()));

        //registrar la salida en SalidaService
        this.salidaService.crearSalida(barcoFound,salidaPatronRequest);

        //personalizar header
        HttpHeaders headers = new HttpHeaders();
        headers.add("custom-header", "Registro salida creado");

        //devolver respuesta
        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body("{\"mensaje\":\" " + "Salida con destino: "
                        + salidaPatronRequest.getDestino()
                        + ", registrado con exito.\"}");

    }

    public ResponseEntity<?> eliminarBarcoSegunID(Integer barcoID) {
        //alamcenar el barcoFound
        Barco barcoFound = this.barcoRepository.findById(barcoID)
                .orElseThrow(() -> new NoSuchElementException("No existe Barco ID: " + barcoID));

        //comprobar que existan registro de salida en cada barco
        this.salidaService.eliminarSalidaSegunBarcoID(barcoID);

        //eliminar cada barco que contenga la lista
        this.barcoRepository.delete(barcoFound);

        //personalizar header
        HttpHeaders headers = new HttpHeaders();
        headers.add("custom-header", "Barco eliminado");

        //devolver respuesta
        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body("{\"mensaje\":\" " + "Barco: "
                        + barcoFound.getNombre()
                        + ", eliminado.\"}");

    }
}
