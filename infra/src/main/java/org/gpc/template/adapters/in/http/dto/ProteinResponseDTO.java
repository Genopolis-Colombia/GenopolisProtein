package org.gpc.template.adapters.in.http.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProteinResponseDTO(
                                UUID idProteina,
                                String fastaNombre,
                                 String fastaSecuencia,
                                 String fuente,
                                 String organismo,
                                 String clasificacion,
                                 Integer ecClasificacion,
                                 String autores) implements DTO {

}
