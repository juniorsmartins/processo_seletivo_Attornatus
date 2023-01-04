package attornatus.cliente.presentation.dtos;

import attornatus.cliente.business.entities.EnderecoEntity;
import attornatus.cliente.business.entities.TipoEnderecoEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

        int numero,

        @NotNull
        TipoEnderecoEnum tipo
    ) implements PolicyDTO<Long>
{
    public EnderecoDTO(EnderecoEntity entities) {
        this(entities.getId(), entities.getLogradouro(), entities.getCep(), entities.getNumero(), entities.getTipo());
    }
}
