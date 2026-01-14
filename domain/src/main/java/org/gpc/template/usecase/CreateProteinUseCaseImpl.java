package org.gpc.template.usecase;

import org.gpc.template.kernel.Protein;
import org.gpc.template.port.RepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;


public class CreateProteinUseCaseImpl implements UseCase<Protein, UUID>{

    private final RepositoryPort repositoryPort;
    private static final Logger logger = LoggerFactory.getLogger(CreateProteinUseCaseImpl.class);

    public CreateProteinUseCaseImpl(RepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public UUID execute(Protein command) {
        logger.debug("Executing command: {}", command);
        return repositoryPort.saveProtein(command);
    }
}
