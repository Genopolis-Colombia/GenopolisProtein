package org.gpc.template.usecase;

import lombok.AllArgsConstructor;
import org.gpc.template.kernel.Protein;
import org.gpc.template.port.RepositoryPort;

import java.util.List;

@AllArgsConstructor
public class GetAllProteinsUseCaseImpl implements UseCase<Void, List<Protein>> {
    private final RepositoryPort repositoryPort;

    @Override
    public List<Protein> execute(Void command) {
        return repositoryPort.getAllProteins();
    }

}
