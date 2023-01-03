package attornatus.cliente.persistence;

import attornatus.cliente.business.entities.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepositoryJPA extends JpaRepository<EnderecoEntity, Long> { }
