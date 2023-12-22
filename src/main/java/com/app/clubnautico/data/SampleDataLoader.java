package com.app.clubnautico.data;

import com.app.clubnautico.auth.AuthenticationService;
import com.app.clubnautico.domain.Barco;
import com.app.clubnautico.domain.Socio;
import com.app.clubnautico.domain.request.BarcoRequest;
import com.app.clubnautico.domain.request.SocioBarcoRequest;
import com.app.clubnautico.services.SocioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class SampleDataLoader implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(SampleDataLoader.class);
    private AuthenticationService authenticationService;
    private SocioService socioService;


    public SampleDataLoader(AuthenticationService authenticationService, SocioService socioService) {
        this.authenticationService = authenticationService;
        this.socioService = socioService;
    }

    @Override
    public void run(String... args) throws Exception {
  /*      logger.info("Cargando datos...");
        //CREACION DE SOCIOS
        SocioBarcoRequest sbr1 = new SocioBarcoRequest(
                "X1234567D",
                "alejandro",
                "brome",
                "63258479",
                "ale@club-nautico.com",
                Set.of(
                new BarcoRequest("conil-I","conil",1,350),
                new BarcoRequest("algeciras-I","algeciras",2,350)
                ));
        SocioBarcoRequest sbr2 = new SocioBarcoRequest(
                "X3658974D",
                "daniel",
                "rodrigo",
                "6874589",
                "danie@club-nautico.com",
                Set.of(
                        new BarcoRequest("poke-I","Picachu",3,150.5),
                        new BarcoRequest("poke-II","Picachu II",4,300)
                ));
        SocioBarcoRequest sbr3 = new SocioBarcoRequest(
                "32569874A",
                "liz",
                "ocampo",
                "625698745",
                "liz@club-nautico.com",
                Set.of(
                        new BarcoRequest("potter-I","Potter",5,350)
                ));
        SocioBarcoRequest sbr4 = new SocioBarcoRequest(
                "12587496A",
                "juanan",
                "olvera",
                "654758239",
                "juanan@club-nautico.com",
                Set.of());
        SocioBarcoRequest sbr5 = new SocioBarcoRequest(
                "35478598A",
                "ana",
                "garrido",
                "611258963",
                "ana@club-nautico.com",
                Set.of(
                        new BarcoRequest("huelva-I","huelva",6,350)
                ));
        SocioBarcoRequest sbr6 = new SocioBarcoRequest(
                "Y6357489D",
                "emilio",
                "garcia",
                "666325879",
                "emilio@club-nautico.com",
                Set.of());
        SocioBarcoRequest sbr7 = new SocioBarcoRequest(
                "15784326A",
                "alejandro",
                "perez",
                "632879888",
                "alejandro@club-nautico.com",
                Set.of());
        SocioBarcoRequest sbr8 = new SocioBarcoRequest(
                "Y3589659D",
                "rebeca",
                "villasmin",
                "663322897",
                "rebeca@club-nautico.com",
                Set.of());
        SocioBarcoRequest sbr9 = new SocioBarcoRequest(
                "X3512578D",
                "rita",
                "muñoz",
                "666325879",
                "rita@club-nautico.com",
                Set.of());
        SocioBarcoRequest sbr10 = new SocioBarcoRequest(
                "95635478A",
                "jesus",
                "pavon",
                "965857152",
                "jesus@club-nautico.com",
                Set.of());
        SocioBarcoRequest sbr11 = new SocioBarcoRequest(
                "Y1258658D",
                "jesus",
                "goitia",
                "666325879",
                "jesus@club-nautico.com",
                Set.of());
        SocioBarcoRequest sbr12 = new SocioBarcoRequest(
                "85478252A",
                "juan",
                "garcia",
                "666325879",
                "juan@club-nautico.com",
                Set.of());


        socioService.crearSocio(sbr1);
        socioService.crearSocio(sbr2);
        socioService.crearSocio(sbr3);
        socioService.crearSocio(sbr4);
        socioService.crearSocio(sbr5);
        socioService.crearSocio(sbr6);
        socioService.crearSocio(sbr7);
        socioService.crearSocio(sbr8);
        socioService.crearSocio(sbr9);
        socioService.crearSocio(sbr10);
        socioService.crearSocio(sbr11);
        socioService.crearSocio(sbr12);
*/

    }

}
