package br.com.portfolio.motor_auditoria_risco.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "registros_auditoria")
public class RegistroAuditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigoSolicitacao;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal valorBase;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal valorImposto;

    @Column(nullable = false)
    private String nivelRisco;

    @Column(nullable = false)
    private LocalDateTime criadoEm;

    public RegistroAuditoria() {
    }

    public RegistroAuditoria(
            String codigoSolicitacao,
            BigDecimal valorBase,
            BigDecimal valorImposto,
            String nivelRisco,
            LocalDateTime criadoEm
    ) {
        this.codigoSolicitacao = codigoSolicitacao;
        this.valorBase = valorBase;
        this.valorImposto = valorImposto;
        this.nivelRisco = nivelRisco;
        this.criadoEm = criadoEm;
    }

    public Long getId() {
        return id;
    }

    public String getCodigoSolicitacao() {
        return codigoSolicitacao;
    }

    public void setCodigoSolicitacao(String codigoSolicitacao) {
        this.codigoSolicitacao = codigoSolicitacao;
    }

    public BigDecimal getValorBase() {
        return valorBase;
    }

    public void setValorBase(BigDecimal valorBase) {
        this.valorBase = valorBase;
    }

    public BigDecimal getValorImposto() {
        return valorImposto;
    }

    public void setValorImposto(BigDecimal valorImposto) {
        this.valorImposto = valorImposto;
    }

    public String getNivelRisco() {
        return nivelRisco;
    }

    public void setNivelRisco(String nivelRisco) {
        this.nivelRisco = nivelRisco;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
}