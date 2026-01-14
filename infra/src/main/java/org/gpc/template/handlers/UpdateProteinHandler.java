package org.gpc.template.handlers;

import lombok.AllArgsConstructor;
import org.gpc.template.adapters.in.http.dto.DTO;
import org.gpc.template.adapters.in.http.dto.ErrorResponse;
import org.gpc.template.adapters.in.http.dto.UpdateProteinRequestDTO;
import org.gpc.template.handlers.commands.UpdateProteinCommand;
import org.gpc.template.kernel.UpdateProtein;
import org.gpc.template.usecase.GetProteinUseCaseImpl;
import org.gpc.template.usecase.PutProteinUseCaseImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

@AllArgsConstructor
public class UpdateProteinHandler implements Handler<UpdateProteinCommand, ResponseEntity<DTO>> {
  private final PutProteinUseCaseImpl putProteinUseCase;
  private final GetProteinUseCaseImpl getProteinUseCase;

  @Override
  public ResponseEntity<DTO> handle(UpdateProteinCommand command) {
    UpdateProteinRequestDTO updateProteinRequestDTO = command.updateProteinRequestDTO();
    UUID proteinID = command.proteinID();
    Optional<String> maybeFastaNombreToBeUpdated = updateProteinRequestDTO.fastaNombre().filter(filterNonEmptyString());
    Optional<String> maybeFastaSecuenciaToBeUpdated = updateProteinRequestDTO.fastaSecuencia().filter(filterNonEmptyString());
    Optional<String> maybeFuenteToBeUpdated = updateProteinRequestDTO.fuente().filter(filterNonEmptyString());
    Optional<String> maybeOrganismoToBeUpdated = updateProteinRequestDTO.organismo().filter(filterNonEmptyString());
    Optional<String> maybeClasificacionToBeUpdated = updateProteinRequestDTO.clasificacion().filter(filterNonEmptyString());
    Optional<Integer> maybeEcClasificacionToBeUpdated = updateProteinRequestDTO.ecClasificacion().filter(filterNonNegativeNumbers());
    Optional<String> maybeAutoresToBeUpdated = updateProteinRequestDTO.autores().filter(filterNonEmptyString());

    Optional<DTO> maybeValidationError = validateFieldsToBeUpdated(

            maybeFastaNombreToBeUpdated,
            maybeFastaSecuenciaToBeUpdated,
            maybeFuenteToBeUpdated,
            maybeOrganismoToBeUpdated,
            maybeClasificacionToBeUpdated,
            maybeEcClasificacionToBeUpdated,
            maybeAutoresToBeUpdated
    );
    return maybeValidationError.map(dto -> new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST)).
        orElseGet(() -> getProteinUseCase.execute(proteinID)
            .map(protein ->
                new UpdateProtein(

                        maybeFastaNombreToBeUpdated.orElse(protein.fastaNombre()),
                        maybeFastaSecuenciaToBeUpdated.orElse(protein.fastaSecuencia()),
                        maybeFuenteToBeUpdated.orElse(protein.fuente()),
                        maybeOrganismoToBeUpdated.orElse(protein.organismo()),
                        maybeClasificacionToBeUpdated.orElse(protein.clasificacion()),
                        maybeEcClasificacionToBeUpdated.orElse(protein.ecClasificacion()),
                        maybeAutoresToBeUpdated.orElse(protein.autores()),

                    proteinID
                )
            ).flatMap(putProteinUseCase::execute)
            .map(updatedProtein -> new ResponseEntity<DTO>(HttpStatus.NO_CONTENT))
            .orElse(new ResponseEntity<>(new ErrorResponse("Invalid protein id", "The provided protein was not found"), HttpStatus.NOT_FOUND))
        );
  }

  private static Predicate<String> filterNonEmptyString() {
    return value -> !value.trim().isEmpty();
  }

  private static Predicate<Integer> filterNonNegativeNumbers() {
    return number -> number > 0;
  }

  private Optional<DTO> validateFieldsToBeUpdated(Optional<String> fastaNombre,
                                                  Optional<String> fastaSecuencia,
                                                  Optional<String> fuente,
                                                  Optional<String> organismo,
                                                  Optional<String> clasificacion,
                                                  Optional<Integer> ecClasificacion,
                                                  Optional<String> autores) {

      boolean allEmpty =
                fastaNombre.isEmpty() &&
                        fastaSecuencia.isEmpty() &&
                        fuente.isEmpty() &&
                        organismo.isEmpty() &&
                        clasificacion.isEmpty() &&
                        ecClasificacion.isEmpty() &&
                        autores.isEmpty();

      if (allEmpty) {
          return Optional.of(
                    new ErrorResponse(
                            "Invalid fields",
                            "At least one field must be provided to update the protein"
                    )
            );
        }

        return Optional.empty();
    }

}
