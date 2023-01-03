package attornatus.cliente.presentation.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record EnderecoDTO
    (
        Long id,

        @NotBlank
        @Length(max = 200)
        String logradouro,

        @NotBlank
        @Length(max = 15)
        String cep,

        int numero
    ) implements PolicyDTO<Long> { }
