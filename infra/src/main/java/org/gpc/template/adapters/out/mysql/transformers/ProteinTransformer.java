package org.gpc.template.adapters.out.mysql.transformers;

import org.gpc.template.adapters.out.mysql.model.ProteinEntity;
import org.gpc.template.kernel.Protein;
import org.gpc.template.kernel.UpdateProtein;

public class ProteinTransformer {

    public static ProteinEntity proteinToEntity(Protein protein) {
        ProteinEntity proteinEntity = new ProteinEntity();
        proteinEntity.setFastaNombre(protein.fastaNombre());
        proteinEntity.setFastaSecuencia(protein.fastaSecuencia());
        proteinEntity.setFuente(protein.fuente());
        proteinEntity.setOrganismo(protein.organismo());
        proteinEntity.setClasificacion(protein.clasificacion());
        proteinEntity.setEcClasificacion(protein.ecClasificacion());
        proteinEntity.setAutores(protein.autores());
        return proteinEntity;
    }

    public static Protein entityToProtein(ProteinEntity proteinEntity) {
        return new Protein(
                proteinEntity.getFastaNombre(),
                proteinEntity.getFastaSecuencia(),
                proteinEntity.getFuente(),
                proteinEntity.getOrganismo(),
                proteinEntity.getClasificacion(),
                proteinEntity.getEcClasificacion(),
                proteinEntity.getAutores()
        );
    }

    public static ProteinEntity updateProteinToEntity(UpdateProtein updateProtein) {
        ProteinEntity proteinEntity = new ProteinEntity();
        proteinEntity.setId(updateProtein.idProteina());
        proteinEntity.setFastaNombre(updateProtein.fastaNombre());
        proteinEntity.setFastaSecuencia(updateProtein.fastaSecuencia());
        proteinEntity.setFuente(updateProtein.fuente());
        proteinEntity.setOrganismo(updateProtein.organismo());
        proteinEntity.setClasificacion(updateProtein.clasificacion());
        proteinEntity.setEcClasificacion(updateProtein.ecClasificacion());
        proteinEntity.setAutores(updateProtein.autores());
        return proteinEntity;
    }
}
