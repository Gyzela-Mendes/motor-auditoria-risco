package br.com.portfolio.motor_auditoria_risco.repository;

import br.com.portfolio.motor_auditoria_risco.model.RegistroAuditoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistroAuditoriaRepository extends JpaRepository<RegistroAuditoria, Long> {
    Optional<RegistroAuditoria> findByCodigoSolicitacao(String codigoSolicitacao);
}