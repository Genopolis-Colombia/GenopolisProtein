package org.gpc.template.configuration;

import org.gpc.template.adapters.out.mysql.MysqlProteinRepositoryImpl;
import org.gpc.template.adapters.out.mysql.ProteinRepository;
import org.gpc.template.handlers.*;
import org.gpc.template.port.RepositoryPort;
import org.gpc.template.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    MysqlProteinRepositoryImpl getMysqlProteinRepositoryImpl(ProteinRepository proteinRepository){
        return new MysqlProteinRepositoryImpl(proteinRepository);
    }
    @Bean
    CreateProteinUseCaseImpl getCreateProteinUseCaseImpl(RepositoryPort repositoryPort){
        return new CreateProteinUseCaseImpl(repositoryPort);
    }

    @Bean
    GetProteinUseCaseImpl getProteinUseCase(RepositoryPort repositoryPort){
        return new GetProteinUseCaseImpl(repositoryPort);
    }

    @Bean
    DeleteProteinUseCaseImpl getDeleteProteinUseCase(RepositoryPort repositoryPort){
        return new DeleteProteinUseCaseImpl(repositoryPort);
    }
    @Bean
    PutProteinUseCaseImpl getPutProteinUseCase(RepositoryPort repositoryPort){
        return new PutProteinUseCaseImpl(repositoryPort);
    }

    @Bean
    GetAllProteinsUseCaseImpl getAllProteinsUseCase(RepositoryPort repositoryPort){
        return new GetAllProteinsUseCaseImpl(repositoryPort);
    }

    @Bean
    UpdateProteinHandler getUpdateProteinHandler(GetProteinUseCaseImpl getProteinUseCase, PutProteinUseCaseImpl putProteinUseCase){
        return new UpdateProteinHandler(putProteinUseCase, getProteinUseCase);
    }

    @Bean
    CreateProteinHandler getCreateProteinHandler(CreateProteinUseCaseImpl createProteinUseCase){
        return new CreateProteinHandler(createProteinUseCase);
    }

    @Bean
    GetProteinHandler getGetProteinHandler(GetProteinUseCaseImpl getProteinUseCase){
        return new GetProteinHandler(getProteinUseCase);
    }

    @Bean
    DeleteProteinHandler getDeleteProteinHandler(DeleteProteinUseCaseImpl deleteProteinUseCase){
        return new DeleteProteinHandler(deleteProteinUseCase);
    }

    @Bean
    GetAllProteinsHandler getAllProteinsHandler(GetAllProteinsUseCaseImpl getAllProteinsUseCase){
        return new GetAllProteinsHandler(getAllProteinsUseCase);
    }
}
