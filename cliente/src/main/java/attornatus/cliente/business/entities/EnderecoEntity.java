package attornatus.cliente.business.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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

}
