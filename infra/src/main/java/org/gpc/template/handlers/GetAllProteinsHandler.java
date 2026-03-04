package org.gpc.template.handlers;

import lombok.AllArgsConstructor;
import org.gpc.template.adapters.in.http.dto.AllProteinsResponseDTO;
import org.gpc.template.adapters.in.http.dto.DTO;
import org.gpc.template.adapters.in.http.dto.ProteinResponseDTO;
import org.gpc.template.usecase.GetAllProteinsUseCaseImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@AllArgsConstructor
public class GetAllProteinsHandler implements Handler<Void, ResponseEntity<DTO>> {
    private GetAllProteinsUseCaseImpl getAllProteinsUseCase;

    @Override
    public ResponseEntity<DTO> handle(Void command) {
        List<ProteinResponseDTO> proteinResponseDTOS = getAllProteinsUseCase.execute(null)
                .stream()
                .map(protein -> (
                        new ProteinResponseDTO(
                                protein.id(),
                                protein.fastaNombre(),
                                protein.fastaSecuencia(),
                                protein.fuente(),
                                protein.organismo(),
                                protein.clasificacion(),
                                protein.ecClasificacion(),
                                protein.autores())
                )).toList();
        return new ResponseEntity<DTO>(new AllProteinsResponseDTO(proteinResponseDTOS), HttpStatus.OK);
    }
}
