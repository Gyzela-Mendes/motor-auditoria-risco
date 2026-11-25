package br.com.portfolio.motor_auditoria_risco.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatus(ResponseStatusException ex) {
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
        String mensagem = ex.getReason() != null ? ex.getReason() : "Erro na requisição";
        return buildErrorResponse(status, mensagem, null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errosCampos = new LinkedHashMap<>();
        for (FieldError erro : ex.getBindingResult().getFieldErrors()) {
            errosCampos.put(erro.getField(), erro.getDefaultMessage());
        }
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Falha de validação nos dados enviados", errosCampos);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleConflict(DataIntegrityViolationException ex) {
        return buildErrorResponse(
                HttpStatus.CONFLICT,
                "Já existe um registro de auditoria com o código informado",
                null
        );
    }

    private ResponseEntity<Object> buildErrorResponse(
            HttpStatus status,
            String mensagem,
            Map<String, String> detalhes
    ) {
        Map<String, Object> corpo = new LinkedHashMap<>();
        corpo.put("timestamp", LocalDateTime.now().toString());
        corpo.put("status", status.value());
        corpo.put("erro", status.getReasonPhrase());
        corpo.put("mensagem", mensagem);
        if (detalhes != null && !detalhes.isEmpty()) {
            corpo.put("campos", detalhes);
        }
        return ResponseEntity.status(status).body(corpo);
    }
}