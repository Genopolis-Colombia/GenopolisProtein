package org.gpc.template.handlers;

import lombok.AllArgsConstructor;
import org.gpc.template.adapters.in.http.dto.DTO;
import org.gpc.template.usecase.DeletePetUseCaseImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
public class DeleteProteinHandler implements Handler<Integer, ResponseEntity<DTO>> {
  private final DeletePetUseCaseImpl deletePetUseCase;

  @Override
  public ResponseEntity<DTO> handle(Integer petID) {
    deletePetUseCase.execute(petID);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
