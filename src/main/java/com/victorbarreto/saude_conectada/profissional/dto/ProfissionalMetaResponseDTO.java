package com.victorbarreto.saude_conectada.profissional.dto;

import java.time.LocalDate;

import com.victorbarreto.saude_conectada.paciente.entity.StatusMeta;

public record ProfissionalMetaResponseDTO(Long id,
                                          String descricao,
                                          LocalDate dataMeta,
                                          StatusMeta status) {
}
