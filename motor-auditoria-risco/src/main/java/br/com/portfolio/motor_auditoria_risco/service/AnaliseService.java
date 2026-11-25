package br.com.portfolio.motor_auditoria_risco.service;

import br.com.portfolio.motor_auditoria_risco.dto.RespostaAnaliseDTO;
import br.com.portfolio.motor_auditoria_risco.dto.SolicitacaoAnaliseDTO;
import br.com.portfolio.motor_auditoria_risco.model.RegistroAuditoria;
import br.com.portfolio.motor_auditoria_risco.repository.RegistroAuditoriaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnaliseService {

    private final RegistroAuditoriaRepository repository;

    public AnaliseService(RegistroAuditoriaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public RespostaAnaliseDTO executarAnalise(SolicitacaoAnaliseDTO solicitacao) {
        BigDecimal valorBase = solicitacao.valorBase();
        BigDecimal aliquota = definirAliquota(valorBase);
        BigDecimal valorImposto = valorBase.multiply(aliquota).setScale(2, RoundingMode.HALF_UP);
        String nivelRisco = classificarRisco(valorBase);

        RegistroAuditoria entidade = new RegistroAuditoria(
                solicitacao.codigoSolicitacao(),
                valorBase,
                valorImposto,
                nivelRisco,
                LocalDateTime.now()
        );

        RegistroAuditoria salvo = repository.save(entidade);
        return toDTO(salvo);
    }

    @Transactional(readOnly = true)
    public List<RespostaAnaliseDTO> listarTodas() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public RespostaAnaliseDTO buscarPorCodigo(String codigoSolicitacao) {
        RegistroAuditoria registro = repository.findByCodigoSolicitacao(codigoSolicitacao)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Análise não encontrada para o código: " + codigoSolicitacao
                ));
        return toDTO(registro);
    }

    private RespostaAnaliseDTO toDTO(RegistroAuditoria salvo) {
        return new RespostaAnaliseDTO(
                salvo.getId(),
                salvo.getCodigoSolicitacao(),
                salvo.getValorBase(),
                salvo.getValorImposto(),
                salvo.getNivelRisco(),
                salvo.getCriadoEm()
        );
    }

    private BigDecimal definirAliquota(BigDecimal valorBase) {
        if (valorBase.compareTo(new BigDecimal("10000.00")) >= 0) {
            return new BigDecimal("0.15");
        }
        if (valorBase.compareTo(new BigDecimal("2000.00")) >= 0) {
            return new BigDecimal("0.10");
        }
        return new BigDecimal("0.05");
    }

    private String classificarRisco(BigDecimal valorBase) {
        if (valorBase.compareTo(new BigDecimal("10000.00")) >= 0) {
            return "ALTO";
        }
        if (valorBase.compareTo(new BigDecimal("2000.00")) >= 0) {
            return "MEDIO";
        }
        return "BAIXO";
    }
}