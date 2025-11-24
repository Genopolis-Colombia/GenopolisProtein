package org.gpc.template.handlers;

import lombok.AllArgsConstructor;
import org.gpc.template.adapters.in.http.dto.DTO;
import org.gpc.template.kernel.Protein;
import org.gpc.template.kernel.Specie;
import org.gpc.template.usecase.CreatePetUseCaseImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
public class CreateProteinHandler implements Handler<CreatePetRequestDTO, ResponseEntity<DTO>> {
  private final CreatePetUseCaseImpl createPetUseCase;

  @Override
  public ResponseEntity<DTO> handle(CreatePetRequestDTO petRequestDto) {
    Integer id = createPetUseCase.execute(new Pet(
        petRequestDto.name(),
        petRequestDto.age(),
        Specie.valueOf(petRequestDto.specie().toUpperCase()),
        petRequestDto.breed()
    ));
    return new ResponseEntity<>(new CreatePetResponseDTO(id), HttpStatus.CREATED);
  }
}
