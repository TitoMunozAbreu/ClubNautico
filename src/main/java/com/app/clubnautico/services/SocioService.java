package com.app.clubnautico.services;

import com.app.clubnautico.domain.Barco;
import com.app.clubnautico.domain.Socio;
import com.app.clubnautico.domain.request.RegistroSalidaSocioRequest;
import com.app.clubnautico.domain.request.SocioBarcoRequest;
import com.app.clubnautico.domain.request.SocioRequest;
import com.app.clubnautico.domain.response.SocioBarcoResponse;
import com.app.clubnautico.domain.response.SocioResponse;
import com.app.clubnautico.repositories.SocioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SocioService {
    private SocioRepository socioRepository;
    private BarcoService barcoService;
    private RegistroSalidaService salidaService;


    @Autowired
    public SocioService(SocioRepository socioRepository,
                        BarcoService barcoService,
                        RegistroSalidaService salidaService) {
        this.socioRepository = socioRepository;
        this.barcoService = barcoService;
        this.salidaService = salidaService;
    }

    @Transactional
    public ResponseEntity<?> crearSocio(SocioBarcoRequest socioBarcoRequest) {

        //instanciar nuevo socio
        Socio newSocio = new Socio();
        //insertar datos a newSocio
        newSocio.setDocumentoIdentidad(socioBarcoRequest.getDocumentoIdentidad());
        newSocio.setNombre(socioBarcoRequest.getNombre());
        newSocio.setApellidos(socioBarcoRequest.getApellidos());
        newSocio.setEmail(socioBarcoRequest.getEmail());
        newSocio.setMovil(socioBarcoRequest.getMovil());

        //almacenar si exise lista de barcos
//        boolean listaBarcoVacia = socioBarcoRequest.getBarcos().isEmpty();

        //comprobar si el socio posee barcos
        if (socioBarcoRequest.getBarcos() != null && !socioBarcoRequest.getBarcos().isEmpty()) {
            //crear cada objeto de la lista en el BarcoRepository
            socioBarcoRequest.getBarcos()
                    .stream()
                    .map(barcoRequest -> {
                        //llamar al metodo crearBarco en BarcoService
                        return this.barcoService.crearBarco(barcoRequest, newSocio);
                    }).collect(Collectors.toSet());
        }
        //personalizar header
        HttpHeaders headers = new HttpHeaders();
        headers.add("custom-header", "Usuario creado");

        //persistir newSocio en la BBDD
        this.socioRepository.save(newSocio);

        //devolver respuesta
        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body(Map.of("mensaje", "Socio: " + newSocio.getNombre() + " " + newSocio.getApellidos() + " creado con exito."));
    }

    @Transactional
    public ResponseEntity<Page<SocioResponse>> listarSocio(String nombre, int page, int size) {
        try {

            boolean listaSocioVacia = this.socioRepository.findAll().isEmpty();
            //comprobar la lista
            if (listaSocioVacia) {
                //lanzar excepcion si esta vacia la lista
                throw new NoSuchElementException("No existen socios creados");
            }

            //mapear la lista encontrada al socioResponse
            Page<SocioResponse> socioPage = this.socioRepository.findByNombreContaining(nombre, PageRequest.of(page, size))
                    .map(socio -> new SocioResponse(
                    socio.getSocioId(),
                    socio.getNombre(),
                    socio.getApellidos()));

/*            List<SocioResponse> socioResponseList = this.socioRepository.findAll(pageable).stream()
                    .map(socio -> {
                        SocioResponse socioResponse = new SocioResponse(
                                socio.getNombre(),
                                socio.getApellidos());
                        return socioResponse;
                    }).collect(Collectors.toList());*/

            //personalizar header
            HttpHeaders headers = new HttpHeaders();
            headers.add("custom-header", "Lista de socios");

            //delvolver el lista de socios
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .headers(headers)
                    .body(socioPage);

        } catch (NoSuchElementException ex) {
            throw new NoSuchElementException(ex.getMessage());
        }
    }

    @Transactional
    public ResponseEntity<SocioBarcoResponse> obtenerSocioByID(Integer socioID) {
        //almancenar el socio encontrado
        Socio socioFound = this.socioRepository.findById(socioID)
                .orElseThrow(() -> new NoSuchElementException("No existe socio ID: " + socioID));

        //mapear socioFound a socioBarcoResponse
        SocioBarcoResponse socioBarcoResponse = SocioBarcoResponse.builder()
                .id(socioFound.getSocioId())
                .nombre(socioFound.getNombre())
                .apellidos(socioFound.getApellidos())
                .email(socioFound.getEmail())
                .movil(socioFound.getMovil())
                .barcos(Set.of())
                .build();

        //almacenar si exise lista de barcos
        boolean listaBarcosVacia = this.barcoService.listarBarcosSegunSocioID(socioID).isEmpty();

        if (!listaBarcosVacia) {
            //almacenar las lista de barcos del socio
            socioBarcoResponse.setBarcos(this.barcoService.listarBarcosSegunSocioID(socioID));
        }

        //personalizar header
        HttpHeaders headers = new HttpHeaders();
        headers.add("custom-header", "obtener socio por ID");

        //devolver el socio
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .headers(headers)
                .body(socioBarcoResponse);

    }

    @Transactional
    public Socio obtenerSocio(Integer socioID) {
        //almancenar el socio encontrado
        return this.socioRepository.findById(socioID)
                .orElseThrow(() -> new NoSuchElementException("No existe socio ID: " + socioID));
    }

    @Transactional
    public ResponseEntity<?> actualizarSociobyID(int socioID, SocioRequest socioRequest) {
        //almacenar socioFound
        Socio socioFound = this.socioRepository.findById(socioID)
                //en caso que no encuentre al socio lanza una excepcion
                .orElseThrow(() -> new NoSuchElementException("No existe socio ID: " + socioID));

        //actualizar datos del socio found
        socioFound.setNombre(socioRequest.getNombre());
        socioFound.setApellidos(socioRequest.getApellidos());
        socioFound.setMovil(socioRequest.getMovil());
        socioFound.setEmail(socioRequest.getEmail());

        //persistir datos BBDD
        this.socioRepository.save(socioFound);
        //personalizar header
        HttpHeaders headers = new HttpHeaders();
        headers.add("custom-header", "Actualizado socio ID: " + socioID);

        //devolver el respuesta
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .headers(headers)
                .body(Map.of("mensaje", "Socio: " + socioFound.getNombre() + " " + socioFound.getApellidos() + "  actualizado con exito."));

    }

    @Transactional
    public ResponseEntity<?> registrarSalidaSocioByID(RegistroSalidaSocioRequest salidaRequest) {

        Integer socioID = salidaRequest.getPatronID();
        Integer barcoID = salidaRequest.getBarcoID();

        //almancenar el socio encontrado
        Socio socioFound = this.socioRepository.findById(socioID)
                .orElseThrow(() -> new NoSuchElementException("No existe socio ID: " + socioID));
        //verificar el barco en BBDD
        Barco barcoFound = this.barcoService.obtenerBarcoSegunSocioID(socioID, barcoID);
        //registar la salida llamando al registroService
        this.salidaService.crearSalida(socioFound, barcoFound, salidaRequest);

        //personalizar header
        HttpHeaders headers = new HttpHeaders();
        headers.add("custom-header", "Registro salida creado");

        //devolver respuesta
        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body("{\"mensaje\":\" " + "Salida con destino: "
                        + salidaRequest.getDestino()
                        + ", registrado con exito.\"}");


    }

    @Transactional
    public ResponseEntity<?> eliminarSocioByID(Integer socioID) {
        //almancenar el socio encontrado
        Socio socioFound = this.socioRepository.findById(socioID)
                .orElseThrow(() -> new NoSuchElementException("No existe socio ID: " + socioID));

        //comprobar que el socio tenga barcos asociados
        this.barcoService.eliminarBarcoSegunSocioID(socioFound.getSocioId());
        //elimnar socio
        this.socioRepository.delete(socioFound);

        //personalizar header
        HttpHeaders headers = new HttpHeaders();
        headers.add("custom-header", "Socio eliminado");

        //devolver respuesta
        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body(Map.of("mensaje", "Socio: " + socioFound.getNombre() + " " + socioFound.getApellidos() + "  ha sido eliminado."));
    }

    @Transactional
    public ResponseEntity<?> existeSocioPorDocumentoIdentidad(String documentoIdentidad){
        boolean existeDocumetoIdentidad = this.socioRepository.existsSocioByDocumentoIdentidad(documentoIdentidad);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("existeDniNie", existeDocumetoIdentidad));
    }

    @Transactional
    public ResponseEntity<?> existeSocioPorEmail(String email){
        boolean existeDocumetoIdentidad = this.socioRepository.existsSocioByEmail(email);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("existeEmail", existeDocumetoIdentidad));
    }



}
