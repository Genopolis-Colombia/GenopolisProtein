package org.gpc.template.handlers.commands;

import org.gpc.template.adapters.in.http.dto.UpdateProteinRequestDTO;

public record UpdatePetCommand(UpdateProteinRequestDTO updateProteinRequestDTO, Integer petID) {
}
