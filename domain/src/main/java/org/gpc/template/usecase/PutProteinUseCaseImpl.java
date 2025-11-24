package org.gpc.template.usecase;

import lombok.AllArgsConstructor;
import org.gpc.template.kernel.Protein;
import org.gpc.template.kernel.UpdateProtein;
import org.gpc.template.port.RepositoryPort;

import java.util.Optional;

@AllArgsConstructor
public class PutProteinUseCaseImpl implements UseCase <UpdateProtein, Optional<Protein>> {

    private final RepositoryPort repositoryPort;

    @Override
    public Optional<Protein> execute (UpdateProtein command){
        return repositoryPort.putProtein(command);
    }
}
