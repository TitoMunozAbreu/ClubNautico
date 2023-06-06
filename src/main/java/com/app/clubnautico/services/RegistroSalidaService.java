package com.app.clubnautico.services;

import com.app.clubnautico.domain.Barco;
import com.app.clubnautico.domain.Patron;
import com.app.clubnautico.domain.RegistroSalida;
import com.app.clubnautico.domain.Socio;
import com.app.clubnautico.domain.request.RegistroSalidaPatronRequest;
import com.app.clubnautico.domain.request.RegistroSalidaSocioRequest;
import com.app.clubnautico.domain.response.PatronResponse;
import com.app.clubnautico.domain.response.RegistroSalidaResponse;
import com.app.clubnautico.repositories.PatronRepository;
import com.app.clubnautico.repositories.RegistroSalidaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RegistroSalidaService {
    private RegistroSalidaRepository salidaRepository;
    private PatronRepository patronRepository;

    @Autowired

    public RegistroSalidaService(RegistroSalidaRepository salidaRepository,
                                 PatronRepository patronRepository) {
        this.salidaRepository = salidaRepository;
        this.patronRepository = patronRepository;
    }

    @Transactional
    public void crearSalida(Socio socioFound, Barco barcoFound, RegistroSalidaSocioRequest salidaSocioRequest) {

        //Almacenar socio como patron
        Patron newPatron = new Patron();
        newPatron.setDocumentoIdentidad(socioFound.getDocumentoIdentidad());
        newPatron.setNombre(socioFound.getNombre());
        newPatron.setApellidos(socioFound.getApellidos());
        newPatron.setMovil(socioFound.getMovil());
        newPatron.setEmail(socioFound.getEmail());

        Patron patron = this.patronRepository.findByDocumentoIdentidad(
                socioFound.getDocumentoIdentidad()).orElse(newPatron);

        //definir el formato de fecha
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        //alamcenar la fecha formateada
        LocalDateTime dateTime = LocalDateTime.parse(salidaSocioRequest.getFechaSalida(),formatter);

        //mapear salidaSocioRequest a Salida
        RegistroSalida newSalida = RegistroSalida.builder()
                .fechaSalida(dateTime)
                .destino(salidaSocioRequest.getDestino())
                .barco(barcoFound)
                .patron(patron)
                .build();

        //almacenar salida
        this.salidaRepository.save(newSalida);

    }

    @Transactional
    public void crearSalida(Barco barcoFound, RegistroSalidaPatronRequest salidaPatronRequest) {
        String patronDocumentoIdentidad = salidaPatronRequest.getPatron().getDocumentoIdentidad();

        //Crear newPatron
        Patron newPatron = new Patron();
        newPatron.setDocumentoIdentidad(salidaPatronRequest.getPatron().getDocumentoIdentidad());
        newPatron.setNombre(salidaPatronRequest.getPatron().getNombre());
        newPatron.setApellidos(salidaPatronRequest.getPatron().getApellidos());
        newPatron.setMovil(salidaPatronRequest.getPatron().getMovil());
        newPatron.setEmail(salidaPatronRequest.getPatron().getEmail());

        //comprobar si el patron existe en la BBDD
        Patron patron = this.patronRepository.obtenerPatronByDocumentoIdentidad(patronDocumentoIdentidad)
                .orElse(newPatron);

        //definir el formato de fecha
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        //alamcenar la fecha formateada
        LocalDateTime dateTime = LocalDateTime.parse(salidaPatronRequest.getFechaSalida(),formatter);

        //mapear salidaRequest a Salida
        RegistroSalida newSalida = RegistroSalida.builder()
                .fechaSalida(dateTime)
                .destino(salidaPatronRequest.getDestino())
                .barco(barcoFound)
                .patron(patron)
                .build();

        //almacenar salida
        this.salidaRepository.save(newSalida);

    }

    @Transactional
    public void eliminarSalidaSegunBarcoID(Integer barcoID) {
        //almacenar lista de salidas segun barcoID
        Set<RegistroSalida> salidasFound = this.salidaRepository.listarSalidaSegunBarcoID(barcoID);

        //comprobar que salidasFound no este vacia
        if(!salidasFound.isEmpty()){
            //iterar sobre la lista salidasFound
            salidasFound.stream()
                    .forEach(salida -> {
                        //eliminar salida segun el barcoID
                        this.salidaRepository.deleteById(salida.getSalidaId());
                    });
        }
    }

    @Transactional
    public ResponseEntity<?> eliminarSalidaSegunID(Integer salidaID) {
        //almacenar salidaFound
        RegistroSalida salidaFound = this.salidaRepository.findById(salidaID)
                .orElseThrow(() -> new NoSuchElementException("No existe salida ID: " + salidaID));
        //eliminar salida
        this.salidaRepository.deleteById(salidaID);

        //personalizar header
        HttpHeaders headers = new HttpHeaders();
        headers.add("custom-header", "Salida eliminada");

        //devolver respuesta
        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body("{\"mensaje\":\" " + "Salida con destino: "
                        + salidaFound.getDestino()
                        + ", eliminada.\"}");


    }

    @Transactional
    public Set<RegistroSalidaResponse>  listarSalidasSegunBarcoID(Integer barcoID) {
        //almacenar la lista de salida de tipo RegistroSalidaResponse
        Set<RegistroSalidaResponse> salidaResponseSet = this.salidaRepository
                .listarSalidaSegunBarcoID(barcoID)
                .stream()
                .map(registroSalida -> {
                    //mapear el patron a patronResponse
                    PatronResponse patronResponse = PatronResponse.builder()
                            .nombre(registroSalida.getPatron().getNombre())
                            .apellidos(registroSalida.getPatron().getApellidos())
                            .build();

                    //mapear el registroSalida a registroSalidaResponse
                    RegistroSalidaResponse salidaResponse = RegistroSalidaResponse.builder()
                            .fechaSalida(registroSalida.getFechaSalida())
                            .destino(registroSalida.getDestino())
                            .patron(patronResponse)
                            .build();

                    return salidaResponse;
                }).collect(Collectors.toSet());

        return salidaResponseSet;
    }
}
