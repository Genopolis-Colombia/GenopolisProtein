package org.gpc.template.adapters.out.mysql;

import org.gpc.template.adapters.out.mysql.model.ProteinEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;


public interface ProteinRepository extends CrudRepository<ProteinEntity, UUID> {

}
