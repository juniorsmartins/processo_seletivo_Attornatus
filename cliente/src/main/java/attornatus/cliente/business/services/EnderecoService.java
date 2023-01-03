package attornatus.cliente.business.services;

import attornatus.cliente.presentation.dtos.EnderecoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public non-sealed class EnderecoService implements PolicyService<EnderecoDTO, Long> {

    @Override
    public EnderecoDTO create(EnderecoDTO dto) {
        return null;
    }

    @Override
    public EnderecoDTO update(EnderecoDTO dto) {
        return null;
    }

    @Override
    public EnderecoDTO find(Long aLong) {
        return null;
    }

    @Override
    public List<EnderecoDTO> findAll() {
        return null;
    }
}
