package br.com.portfolio.motor_auditoria_risco.controller;

import br.com.portfolio.motor_auditoria_risco.dto.RespostaAnaliseDTO;
import br.com.portfolio.motor_auditoria_risco.dto.SolicitacaoAnaliseDTO;
import br.com.portfolio.motor_auditoria_risco.service.AnaliseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/analises")
public class AnaliseController {

    private final AnaliseService analiseService;

    public AnaliseController(AnaliseService analiseService) {
        this.analiseService = analiseService;
    }

    @PostMapping
    public ResponseEntity<RespostaAnaliseDTO> criarAnalise(
            @Valid @RequestBody SolicitacaoAnaliseDTO solicitacao) {
        RespostaAnaliseDTO resposta = analiseService.executarAnalise(solicitacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @GetMapping
    public ResponseEntity<List<RespostaAnaliseDTO>> listarAnalises() {
        return ResponseEntity.ok(analiseService.listarTodas());
    }

    @GetMapping("/{codigoSolicitacao}")
    public ResponseEntity<RespostaAnaliseDTO> buscarPorCodigo(
            @PathVariable String codigoSolicitacao) {
        return ResponseEntity.ok(analiseService.buscarPorCodigo(codigoSolicitacao));
    }
}