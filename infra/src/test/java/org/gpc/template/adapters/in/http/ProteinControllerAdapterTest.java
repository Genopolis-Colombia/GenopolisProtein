package org.gpc.template.adapters.in.http;

import org.gpc.template.MySQLTestContainer;
import org.gpc.template.adapters.in.http.dto.CreateProteinRequestDTO;
import org.gpc.template.adapters.in.http.dto.UpdateProteinRequestDTO;
import org.gpc.template.kernel.Protein;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProteinControllerAdapterTest extends MySQLTestContainer {

    @Value("${local.server.port}")
    private Integer port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String host = "http://localhost:";

    @Test
    void shouldSaveAProteinSuccessful() {
        String path = host + port + "/pets";
        CreateProteinRequestDTO entity = new CreateProteinRequestDTO("P53", "MEEPQSDPSV", "Human", "Homo sapiens", "Transcription Factor", 1234, "Levine et al.");
        HttpEntity<CreateProteinRequestDTO> request = new HttpEntity<>(entity);
        ResponseEntity<CreateProteinRequestDTO> response = restTemplate.exchange(path, HttpMethod.POST, request, CreateProteinRequestDTO.class);
        Optional<Protein> maybeProteinSaved = mySQLProteinRepository.getProtein(Objects.requireNonNull(response.getBody()).id());

        validateSuccessfulResponse(response);
        assertTrue(maybeProteinSaved.isPresent());
        assertEquals(entity.fastaNombre(), maybeProteinSaved.get().fastaNombre());
        assertEquals(entity.fastaSecuencia(), maybeProteinSaved.get().fastaSecuencia());
        assertEquals(entity.fuente(), maybeProteinSaved.get().fuente());
        assertEquals(entity.organismo(), maybeProteinSaved.get().fastaNombre());
        assertEquals(entity.clasificacion(), maybeProteinSaved.get().clasificacion());
        assertEquals(entity.ecClasificacion(), maybeProteinSaved.get().ecClasificacion());
        assertEquals(entity.autores(), maybeProteinSaved.get().autores());
    }

    @Test
    void shouldGetAProteinSuccessful() {
        Protein expectedProtein = new Protein("PMM2", "MEEPQSDPSV", "Human", "Homo sapiens", "Transcription Factor", 1234, "Levine et al.", 2012-12-03,2025-12-06);
        UUID id = mySQLProteinRepository.saveProtein(expectedProtein);
        String path = host + port + "/proteins" + "/" + id;
        ResponseEntity<Protein> response = restTemplate.exchange(path, HttpMethod.GET, null, Protein.class);

        validateSuccessfulResponse(response);
        assertEquals(expectedProtein.fastaNombre(), Objects.requireNonNull(response.getBody()).fastaNombre());
        assertEquals(expectedProtein.fastaSecuencia(), Objects.requireNonNull(response.getBody()).fastaSecuencia());
        assertEquals(expectedProtein.fuente(), Objects.requireNonNull(response.getBody()).fuente());
        assertEquals(expectedProtein.organismo(), Objects.requireNonNull(response.getBody()).organismo());
        assertEquals(expectedProtein.clasificacion(), Objects.requireNonNull(response.getBody()).clasificacion());
        assertEquals(expectedProtein.ecClasificacion(), Objects.requireNonNull(response.getBody()).ecClasificacion());
        assertEquals(expectedProtein.autores(), Objects.requireNonNull(response.getBody()).autores());
    }

    @Test
    void shouldUpdateAProteinSuccessful() {
        Protein updatedProtein = new Protein("OldName", "AAAA", "Bacteria", "E.coli", "Enzyme", 111, "Dr. X");
        UUID id = mySQLProteinRepository.saveProtein(updatedProtein);
        String path = host + port + "/proteins" + "/" + id;

        Protein expectedProtein = new Protein("ProteinaB", "AAAA", "Bacteria", "E.coli", "Enzyme", 111, "Dr. X");
        UpdateProteinRequestDTO entity = new UpdateProteinRequestDTO(
                Optional.of(expectedProtein.fastaNombre()),
                Optional.of(expectedProtein.fastaSecuencia()),
                Optional.of(expectedProtein.fuente()),
                Optional.of(expectedProtein.organismo()),
                Optional.of(expectedProtein.clasificacion()),
                Optional.of(expectedProtein.ecClasificacion()),
                Optional.of(expectedProtein.autores())
        );

        HttpEntity<UpdateProteinRequestDTO> request = new HttpEntity<>(entity);
        ResponseEntity<Object> response = restTemplate.exchange(path, HttpMethod.PUT, request, Object.class);

        validateSuccessfulResponse(response);
        Optional<Protein> maybeProtein = mySQLProteinRepository.getProtein(id);
        assertTrue(maybeProtein.isPresent());
        assertEquals(expectedProtein.name(), maybeProtein.map(Protein::name).orElse("Invalid"));
        assertEquals(expectedProtein.age(), maybeProtein.map(Protein::age).orElse(-1));
        assertEquals(expectedProtein.specie(), maybeProtein.map(Protein::specie).orElse(Specie.DOG));
        assertEquals(expectedProtein.breed(), maybeProtein.map(Protein::breed).orElse("Invalid"));
    }

    @Test
    void shouldDeleteAPetSuccessful() {
        Protein petToBeDeleted = new Protein("Mauricio", 2, Specie.CAT, "Criollito");
        Integer id = mySQLProteinRepository.savePet(petToBeDeleted);
        String path = host + port + "/pets" + "/" + id;

        ResponseEntity<Integer> response = restTemplate.exchange(path, HttpMethod.DELETE, null, Integer.class);
        validateSuccessfulResponse(response);
        Optional<Pet> maybePet = mySQLPetRepository.getPet(id);
        assertTrue(maybePet.isEmpty());
    }

    private <T> void validateSuccessfulResponse(ResponseEntity<T> response) {
        System.out.println("Response: " + response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }
}