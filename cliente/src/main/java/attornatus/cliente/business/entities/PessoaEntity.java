package attornatus.cliente.business.entities;

import attornatus.cliente.presentation.dtos.PessoaDTO;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "pessoas")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public final class PessoaEntity implements PolicyEntity<Long>, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @OneToMany(mappedBy = "pessoa", cascade = {CascadeType.PERSIST}, orphanRemoval = true, fetch = FetchType.EAGER, targetEntity = EnderecoEntity.class)
    private List<EnderecoEntity> enderecos;

    public PessoaEntity(PessoaDTO dto) {
        this.nome = dto.nome();
        this.dataNascimento = dto.dataNascimento();
        this.enderecos = dto.enderecos()
                .stream()
                .map(EnderecoEntity::new)
                .toList();
    }
}
