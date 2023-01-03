package attornatus.cliente.business.services;

import attornatus.cliente.presentation.dtos.PessoaDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public non-sealed class PessoaService implements PolicyService<PessoaDTO, Long> {

    @Override
    public PessoaDTO create(PessoaDTO dto) {
        return null;
    }

    @Override
    public PessoaDTO update(PessoaDTO dto) {
        return null;
    }

    @Override
    public PessoaDTO find(Long aLong) {
        return null;
    }

    @Override
    public List<PessoaDTO> findAll() {
        return null;
    }
}
