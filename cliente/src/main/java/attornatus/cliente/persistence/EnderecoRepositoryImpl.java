package attornatus.cliente.persistence;

import attornatus.cliente.business.entities.EnderecoEntity;
import attornatus.cliente.business.ports.PolicyEnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EnderecoRepositoryImpl implements PolicyEnderecoRepository<EnderecoEntity, Long> {

    @Autowired
    private EnderecoRepositoryJPA enderecoRepositoryJPA;

    @Override
    public EnderecoEntity save(EnderecoEntity entity) {
        return this.enderecoRepositoryJPA.save(entity);
    }

    @Override
    public List<EnderecoEntity> findAll() {
        return this.enderecoRepositoryJPA.findAll();
    }
}
