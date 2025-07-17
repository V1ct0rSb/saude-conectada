package com.victorbarreto.saude_conectada.paciente.dto;

import java.time.LocalDate;

public record PacientePerfilDTO(Double altura, LocalDate dataNascimento, String comorbidades, Double peso) {
}
