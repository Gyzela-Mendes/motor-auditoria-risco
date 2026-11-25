package br.com.portfolio.motor_auditoria_risco.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RespostaAnaliseDTO(
        Long id,
        String codigoSolicitacao,
        BigDecimal valorBase,
        BigDecimal valorImposto,
        String nivelRisco,
        LocalDateTime criadoEm
) {
}