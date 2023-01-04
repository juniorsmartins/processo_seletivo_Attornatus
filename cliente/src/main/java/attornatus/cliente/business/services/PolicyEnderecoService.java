package attornatus.cliente.business.services;

import attornatus.cliente.presentation.dtos.PolicyDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public sealed interface PolicyEnderecoService<T extends PolicyDTO<ID>, ID> permits EnderecoService {

    T create(ID pessoaId, T dto);
    List<T> findAll();
}
