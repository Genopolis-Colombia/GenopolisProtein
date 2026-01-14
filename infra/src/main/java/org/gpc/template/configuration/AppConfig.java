package org.gpc.template.configuration;

import org.gpc.template.adapters.out.mysql.MysqlProteinRepositoryImpl;
import org.gpc.template.adapters.out.mysql.ProteinRepository;
import org.gpc.template.handlers.CreateProteinHandler;
import org.gpc.template.handlers.DeleteProteinHandler;
import org.gpc.template.handlers.GetProteinHandler;
import org.gpc.template.handlers.UpdateProteinHandler;
import org.gpc.template.port.RepositoryPort;
import org.gpc.template.usecase.CreateProteinUseCaseImpl;
import org.gpc.template.usecase.DeleteProteinUseCaseImpl;
import org.gpc.template.usecase.GetProteinUseCaseImpl;
import org.gpc.template.usecase.PutProteinUseCaseImpl;
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

}
