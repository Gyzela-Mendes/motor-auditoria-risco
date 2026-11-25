# Motor de Auditoria de Risco

API REST desenvolvida em **Java 21** e **Spring Boot 4.2.0** para cálculo de impostos, classificação de risco e persistência de registros de auditoria com **Spring Data JPA** e **H2 Database**.

## Arquitetura e Pacotes
Projeto estruturado em camadas dentro do pacote base `br.com.portfolio.motor_auditoria_risco`:
- `controller` — Endpoints REST (`AnaliseController`)
- `service` — Regras de negócio, cálculo de imposto, classificação de risco e consultas (`AnaliseService`)
- `repository` — Acesso a dados com Spring Data JPA (`RegistroAuditoriaRepository`)
- `model` — Entidade JPA (`RegistroAuditoria`)
- `dto` — Contratos de entrada e saída (`SolicitacaoAnaliseDTO`, `RespostaAnaliseDTO`)
- `exception` — Tratamento global de erros em JSON (`GlobalExceptionHandler`)

## Regras de Negócio
Com base no `valorBase`:
- **A partir de R$ 10.000,00:** alíquota de **15%** e risco **ALTO**
- **De R$ 2.000,00 a R$ 9.999,99:** alíquota de **10%** e risco **MEDIO**
- **Abaixo de R$ 2.000,00:** alíquota de **5%** e risco **BAIXO**

## Endpoints
- `POST /api/analises` — Calcula imposto e risco e salva o registro (`201 Created`, `400 Bad Request` ou `409 Conflict`).
- `GET /api/analises` — Lista todos os registros de auditoria (`200 OK`).
- `GET /api/analises/{codigoSolicitacao}` — Busca um registro pelo código (`200 OK` ou `404 Not Found`).

## Como executar
1. Abra o projeto no **IntelliJ IDEA** com **Java 21** configurado.
2. Execute a classe `MotorAuditoriaRiscoApplication`.
3. Acesse:
   - Swagger UI: `http://localhost:8080/swagger-ui.html`
   - H2 Console: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:auditoriadb`, Usuário: `SA`, Senha em branco)
