package attornatus.cliente.persistence;

import attornatus.cliente.business.entities.PessoaEntity;
import attornatus.cliente.business.ports.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class PessoaRepositoryImpl implements PolicyRepository<PessoaEntity, Long> {

    @Autowired
    private PessoaRepositoryJPA pessoaRepositoryJPA;

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    @Override
    public PessoaEntity save(PessoaEntity entity) {
        return this.pessoaRepositoryJPA.save(entity);
    }

    @Override
    public Optional<PessoaEntity> find(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<PessoaEntity> findAll() {
        return null;
    }
}
