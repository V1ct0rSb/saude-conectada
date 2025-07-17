package com.victorbarreto.saude_conectada.paciente.entity;

import java.time.LocalDate;

import com.victorbarreto.saude_conectada.usuario.entity.UsuarioModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_dados_paciente")
public class PacienteDadosModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double peso;
    private Double altura;
    private LocalDate dataNascimento;
    private String comorbidades;

    @OneToOne
    @JoinColumn(name = "paciente_id", referencedColumnName = "id")
    private UsuarioModel usuarioModel;

    public PacienteDadosModel() {
    }

    public PacienteDadosModel(Double altura,
                              LocalDate dataNascimento,
                              String comorbidades,
                              Double peso,
                              UsuarioModel usuarioModel) {
        this.altura = altura;
        this.dataNascimento = dataNascimento;
        this.comorbidades = comorbidades;
        this.peso = peso;
        this.usuarioModel = usuarioModel;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public String getComorbidades() {
        return comorbidades;
    }

    public void setComorbidades(String comorbidades) {
        this.comorbidades = comorbidades;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public UsuarioModel getUsuarioModel() {
        return usuarioModel;
    }

    public void setUsuarioModel(UsuarioModel usuarioModel) {
        this.usuarioModel = usuarioModel;
    }
}
