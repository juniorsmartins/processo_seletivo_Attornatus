package attornatus.cliente.persistence;

import attornatus.cliente.business.entities.PessoaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepositoryJPA extends JpaRepository<PessoaEntity, Long> { }
