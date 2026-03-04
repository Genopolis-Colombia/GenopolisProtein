package org.gpc.template.kernel;

import java.time.LocalDateTime;
import java.util.UUID;

public record Protein(
        UUID id,
        String fastaNombre,
        String fastaSecuencia,
        String fuente,
        String organismo,
        String clasificacion,
        Integer ecClasificacion,
        String autores) {

}




