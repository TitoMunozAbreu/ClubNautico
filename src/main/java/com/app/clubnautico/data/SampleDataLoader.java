package com.app.clubnautico.data;

import com.app.clubnautico.domain.Barco;
import com.app.clubnautico.domain.Socio;
import com.app.clubnautico.repositories.BarcoRepository;
import com.app.clubnautico.repositories.SocioRepository;
import com.github.javafaker.Faker;
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
    private SocioRepository socioRepository;
    private BarcoRepository barcoRepository;
    private Faker faker;

    public SampleDataLoader(SocioRepository socioRepository, BarcoRepository barcoRepository, Faker faker) {
        this.socioRepository = socioRepository;
        this.barcoRepository = barcoRepository;
        this.faker = faker;
    }

    @Override
    public void run(String... args) throws Exception {
/*        logger.info("Loading Sample Data");

        List<Socio> socios = IntStream.rangeClosed(1, 100)
                .mapToObj(i -> {
                    Socio socio = new Socio();
                    socio.setNombre(faker.name().firstName());
                    socio.setApellidos(faker.name().lastName());
                    socio.setMovil(String.format("%09d", faker.number().numberBetween(1,999999999)));
                    socio.setEmail(setUserEmailDomain(faker.name().username()));
                    socio.setDocumentoIdentidad(truncateTo9Characters(faker.idNumber().valid()));

                    return socio;
                }).toList();

        socioRepository.saveAll(socios);*/
    }

    private String truncateTo9Characters(String input) {
        return input.substring(0, Math.min(input.length(), 9));
    }

    public String setUserEmailDomain(String username){
        return username + "@club-nautico.com";
    }
}
