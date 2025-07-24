package com.victorbarreto.saude_conectada.profissional.entity;

import com.victorbarreto.saude_conectada.usuario.entity.UsuarioModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_dados_profissional")
public class ProfissionalDadosModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String crm;
    private String ufCrm;
    private String especialidade;

    @OneToOne
    @JoinColumn(name = "profissional_id", referencedColumnName = "id")
    private UsuarioModel usuarioModel;

    public ProfissionalDadosModel() {
    }

    public ProfissionalDadosModel(String crm, String especialidade, String ufCrm, UsuarioModel usuarioModel) {
        this.crm = crm;
        this.especialidade = especialidade;
        this.ufCrm = ufCrm;
        this.usuarioModel = usuarioModel;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUfCrm() {
        return ufCrm;
    }

    public void setUfCrm(String ufCrm) {
        this.ufCrm = ufCrm;
    }

    public UsuarioModel getUsuarioModel() {
        return usuarioModel;
    }

    public void setUsuarioModel(UsuarioModel usuarioModel) {
        this.usuarioModel = usuarioModel;
    }
}
