package org.gpc.template.port;

import org.gpc.template.kernel.Protein;
import org.gpc.template.kernel.UpdateProtein;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryPort {
    UUID saveProtein(Protein protein);

    Optional<Protein> getProtein(UUID id);

    public List<Protein> getAllProteins();

    void deleteProtein(UUID id);

    Optional<Protein> putProtein(UpdateProtein updateProtein);

    void deleteAll();
}
