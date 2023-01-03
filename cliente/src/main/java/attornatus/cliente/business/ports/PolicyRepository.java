package attornatus.cliente.business.ports;

import attornatus.cliente.business.entities.PolicyEntity;
import attornatus.cliente.persistence.EnderecoRepositoryImpl;
import attornatus.cliente.persistence.PessoaRepositoryImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PolicyRepository<E extends PolicyEntity<ID>, ID> {

    E save(E entity);
    Optional<E> find(ID id);
    List<E> findAll();
}
