package attornatus.cliente.persistence;

import attornatus.cliente.business.entities.EnderecoEntity;
import attornatus.cliente.business.ports.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class EnderecoRepositoryImpl implements PolicyRepository<EnderecoEntity, Long> {

    @Autowired
    private EnderecoRepositoryJPA enderecoRepositoryJPA;

    @Override
    public EnderecoEntity save(EnderecoEntity entity) {
        return null;
    }

    @Override
    public Optional<EnderecoEntity> find(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<EnderecoEntity> findAll() {
        return null;
    }
}
