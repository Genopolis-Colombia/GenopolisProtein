package org.gpc.template.adapters.in.http;

import org.gpc.template.MySQLTestContainer;
import org.gpc.template.adapters.in.http.dto.CreatePetRequestDTO;
import org.gpc.template.adapters.in.http.dto.UpdateProteinRequestDTO;
import org.gpc.template.kernel.Pet;
import org.gpc.template.kernel.Specie;
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
    void shouldSaveAPetSuccessful() {
        String path = host + port + "/pets";
        CreatePetRequestDTO entity = new CreatePetRequestDTO("Moris", 3, "dog", "chihuahua ");
        HttpEntity<CreatePetRequestDTO> request = new HttpEntity<>(entity);
        ResponseEntity<CreatePetResponseDTO> response = restTemplate.exchange(path, HttpMethod.POST, request, CreatePetResponseDTO.class);
        Optional<Pet> maybePetSaved = mySQLPetRepository.getPet(Objects.requireNonNull(response.getBody()).id());

        validateSuccessfulResponse(response);
        assertTrue(maybePetSaved.isPresent());
        assertEquals(entity.name(), maybePetSaved.get().name());
        assertEquals(entity.age(), maybePetSaved.get().age());
        assertEquals(entity.specie().toUpperCase(), maybePetSaved.get().specie().toString());
        assertEquals(entity.breed(), maybePetSaved.get().breed());
    }

    @Test
    void shouldGetAPetSuccessful() {
        Pet expectedPet = new Pet("Mauricio", 2, Specie.CAT, "Criollito");
        Integer id = mySQLPetRepository.savePet(expectedPet);
        String path = host + port + "/pets" + "/" + id;
        ResponseEntity<Pet> response = restTemplate.exchange(path, HttpMethod.GET, null, Pet.class);

        validateSuccessfulResponse(response);
        assertEquals(expectedPet.name(), Objects.requireNonNull(response.getBody()).name());
        assertEquals(expectedPet.age(), Objects.requireNonNull(response.getBody()).age());
        assertEquals(expectedPet.specie(), Objects.requireNonNull(response.getBody()).specie());
        assertEquals(expectedPet.breed(), Objects.requireNonNull(response.getBody()).breed());
    }

    @Test
    void shouldUpdateAPetSuccessful() {
        Pet oudatedPet = new Pet("Mauricio", 2, Specie.CAT, "Criollito");
        Integer id = mySQLPetRepository.savePet(oudatedPet);
        String path = host + port + "/pets" + "/" + id;

        Pet expectedPet = new Pet("Makarritas", 1, Specie.CAT, "Criollito");
        UpdateProteinRequestDTO entity = new UpdateProteinRequestDTO(
            Optional.of(expectedPet.name()),
            Optional.of(expectedPet.age()),
            Optional.empty(),
            Optional.empty());
        HttpEntity<UpdateProteinRequestDTO> request = new HttpEntity<>(entity);
        ResponseEntity<Object> response = restTemplate.exchange(path, HttpMethod.PUT, request, Object.class);

        validateSuccessfulResponse(response);
        Optional<Pet> maybePet = mySQLPetRepository.getPet(id);
        assertTrue(maybePet.isPresent());
        assertEquals(expectedPet.name(), maybePet.map(Pet::name).orElse("Invalid"));
        assertEquals(expectedPet.age(), maybePet.map(Pet::age).orElse(-1));
        assertEquals(expectedPet.specie(), maybePet.map(Pet::specie).orElse(Specie.DOG));
        assertEquals(expectedPet.breed(), maybePet.map(Pet::breed).orElse("Invalid"));
    }

    @Test
    void shouldDeleteAPetSuccessful() {
        Pet petToBeDeleted = new Pet("Mauricio", 2, Specie.CAT, "Criollito");
        Integer id = mySQLPetRepository.savePet(petToBeDeleted);
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