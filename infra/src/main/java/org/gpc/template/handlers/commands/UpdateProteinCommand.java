package org.gpc.template.handlers.commands;

import org.gpc.template.adapters.in.http.dto.UpdateProteinRequestDTO;

import java.util.UUID;

public record UpdateProteinCommand(UpdateProteinRequestDTO updateProteinRequestDTO, UUID proteinID) {
}
