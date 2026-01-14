package org.gpc.template.adapters.in.http.dto;

import java.util.UUID;

public record CreateProteinRequestDTO(String fastaNombre,
                                      String fastaSecuencia,
                                      String fuente,
                                      String organismo,
                                      String clasificacion,
                                      Integer ecClasificacion,
                                      String autores
) {

}
