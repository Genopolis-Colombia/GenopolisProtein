package org.gpc.template.adapters.in.http.dto;

import java.time.LocalDateTime;

public record ProteinResponseDTO(String fastaNombre,
                                 String fastaSecuencia,
                                 String fuente,
                                 String organismo,
                                 String clasificacion,
                                 Integer ecClasificacion,
                                 String autores,
                                 LocalDateTime creadoEn,
                                 LocalDateTime actualizadoEn) implements DTO {

}
