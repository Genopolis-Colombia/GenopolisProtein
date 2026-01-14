package org.gpc.template.kernel;

import java.util.UUID;

public record UpdateProtein (String fastaNombre, String fastaSecuencia, String fuente, String organismo,
                             String clasificacion, Integer ecClasificacion, String autores, UUID idProteina){

}
