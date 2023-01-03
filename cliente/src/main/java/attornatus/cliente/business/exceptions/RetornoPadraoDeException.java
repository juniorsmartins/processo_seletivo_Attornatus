package attornatus.cliente.business.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RetornoPadraoDeException
    (
        String status,
        String annotations,
        String fieldName,
        String mensagem
    )
{ }
