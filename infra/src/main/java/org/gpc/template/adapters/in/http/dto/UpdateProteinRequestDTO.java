package org.gpc.template.adapters.in.http.dto;

import java.util.Optional;
import java.util.UUID;

public record UpdateProteinRequestDTO(
        UUID proteinId,
        Optional<String> fastaNombre,
        Optional<String> fastaSecuencia,
        Optional<String> fuente,
        Optional<String> organismo,
        Optional<String> clasificacion,
        Optional<Integer> ecClasificacion,
        Optional<String> autores

) { }
