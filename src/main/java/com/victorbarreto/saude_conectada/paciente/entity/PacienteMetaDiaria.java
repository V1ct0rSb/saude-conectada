package com.victorbarreto.saude_conectada.paciente.entity;

import java.time.LocalDate;

import com.victorbarreto.saude_conectada.usuario.entity.UsuarioModel;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_metas_diarias")
public class PacienteMetaDiaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private UsuarioModel paciente;
    private String descricao;
    @Enumerated(EnumType.STRING)
    private StatusMeta status;
    private LocalDate dataMeta;

    public PacienteMetaDiaria() {
    }

    public PacienteMetaDiaria(LocalDate dataMeta, String descricao, UsuarioModel paciente, StatusMeta status) {
        this.dataMeta = dataMeta;
        this.descricao = descricao;
        this.paciente = paciente;
        this.status = status;
    }

    public LocalDate getDataMeta() {
        return dataMeta;
    }

    public void setDataMeta(LocalDate dataMeta) {
        this.dataMeta = dataMeta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioModel getPaciente() {
        return paciente;
    }

    public void setPaciente(UsuarioModel paciente) {
        this.paciente = paciente;
    }

    public StatusMeta getStatus() {
        return status;
    }

    public void setStatus(StatusMeta status) {
        this.status = status;
    }
}
