package attornatus.cliente.business.services;

import attornatus.cliente.presentation.dtos.PolicyDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public sealed interface PolicyService<T extends PolicyDTO<ID>, ID> permits PessoaService, EnderecoService {

    T create(T dto);
    T update(T dto);
    T find(ID id);
    List<T> findAll();
}
