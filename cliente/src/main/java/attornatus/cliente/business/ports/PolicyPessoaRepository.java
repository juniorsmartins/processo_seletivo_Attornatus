package attornatus.cliente.business.ports;

import attornatus.cliente.business.entities.PolicyEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PolicyPessoaRepository<E extends PolicyEntity<ID>, ID> {

    E save(E entity);
    Optional<E> findById(ID id);
    List<E> findAll();
}
