package attornatus.cliente.business.services;

import attornatus.cliente.business.entities.EnderecoEntity;
import attornatus.cliente.business.entities.PessoaEntity;
import attornatus.cliente.business.ports.PolicyRepository;
import attornatus.cliente.presentation.dtos.PessoaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public non-sealed class PessoaService implements PolicyService<PessoaDTO, Long> {

    @Autowired
    private PolicyRepository<PessoaEntity, Long> repository;

    @Override
    public PessoaDTO create(PessoaDTO dto) {
        return Optional.of(dto)
                .map(PessoaEntity::new)
                .map(pessoaNova -> {
                    pessoaNova.getEnderecos().forEach(endereco -> endereco.setPessoa(pessoaNova));
                    return this.repository.save(pessoaNova);
                })
                .map(PessoaDTO::new)
                .orElseThrow();
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
