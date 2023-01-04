package attornatus.cliente.presentation.dtos;

import attornatus.cliente.business.entities.PessoaEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PessoaDTO
    (
        Long id,

        @NotBlank
        @Length(max = 150)
        String nome,

        @NotNull
        @PastOrPresent
        @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
        @JsonDeserialize(using = LocalDateDeserializer.class)
        @JsonSerialize(using = LocalDateSerializer.class)
        LocalDate dataNascimento,

        @NotNull
        @Valid
        List<EnderecoDTO> enderecos
    ) implements PolicyDTO<Long>
{
    public PessoaDTO(PessoaEntity entity) {
        this(entity.getId(),
                entity.getNome(),
                entity.getDataNascimento(),
                entity.getEnderecos()
                        .stream()
                        .map(EnderecoDTO::new)
                        .collect(Collectors.toList())
        );
    }
}

