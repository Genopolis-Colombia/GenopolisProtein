package org.gpc.template.adapters.out.mysql.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
public class ProteinEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;
    private String fastaNombre;
    private String fastaSecuencia;
    private String fuente;
    private String organismo;
    private String clasificacion;
    private Integer ecClasificacion;
    private String autores;
}
