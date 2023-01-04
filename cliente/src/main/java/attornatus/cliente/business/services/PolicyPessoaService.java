package attornatus.cliente.business.services;

import attornatus.cliente.presentation.dtos.PolicyDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public sealed interface PolicyPessoaService<T extends PolicyDTO<ID>, ID> permits PessoaService {

    T create(T dto);
    T update(T dto);
    T findById(ID id);
    List<T> findAll();
}
