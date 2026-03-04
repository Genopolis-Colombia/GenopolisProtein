package org.gpc.template.adapters.in.http.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record AllProteinsResponseDTO(
        @JsonProperty("proteins") List<ProteinResponseDTO> proteinResponseDTOS) implements DTO {
}
