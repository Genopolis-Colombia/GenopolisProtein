package org.gpc.template.usecase;

import lombok.AllArgsConstructor;
import org.gpc.template.kernel.Protein;
import org.gpc.template.port.RepositoryPort;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class GetProteinUseCaseImpl implements UseCase<UUID, Optional<Protein>> {

    private final RepositoryPort repositoryPort;
    @Override
    public Optional<Protein> execute(UUID id) {
        return repositoryPort.getProtein(id);
    }
}
