package br.com.portfolio.motor_auditoria_risco.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record SolicitacaoAnaliseDTO(
        @NotBlank(message = "O código da solicitação é obrigatório")
        String codigoSolicitacao,

        @NotNull(message = "O valor base é obrigatório")
        @DecimalMin(value = "0.01", message = "O valor base deve ser maior que zero")
        BigDecimal valorBase
) {
}