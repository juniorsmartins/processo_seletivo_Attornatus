package attornatus.cliente.business.entities;

import attornatus.cliente.presentation.dtos.EnderecoDTO;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "enderecos")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public final class EnderecoEntity implements PolicyEntity<Long>, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "logradouro")
    private String logradouro;

    @Column(name = "cep")
    private String cep;

    @Column(name = "numero")
    private int numero;

    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private TipoEnderecoEnum tipo;

    @Column(name = "cidade")
    private String cidade;

    @ManyToOne
    @JoinColumn(name = "pessoa_id", referencedColumnName = "id")
    private PessoaEntity pessoa;

    public EnderecoEntity(EnderecoDTO dto) {
        this.logradouro = dto.logradouro();
        this.cep = dto.cep();
        this.numero = dto.numero();
        this.tipo = dto.tipo();
        this.cidade = dto.cidade();
    }
}
