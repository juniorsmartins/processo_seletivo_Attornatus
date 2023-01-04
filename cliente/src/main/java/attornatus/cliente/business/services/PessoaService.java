package attornatus.cliente.business.services;

import attornatus.cliente.business.entities.PessoaEntity;
import attornatus.cliente.business.exceptions.ExceptionEntidadeNaoEncontrada;
import attornatus.cliente.business.ports.PolicyPessoaRepository;
import attornatus.cliente.presentation.dtos.PessoaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public non-sealed class PessoaService implements PolicyPessoaService<PessoaDTO, Long> {

    @Autowired
    private PolicyPessoaRepository<PessoaEntity, Long> repository;

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
    public PessoaDTO findById(Long id) {

        return this.repository.findById(id)
                .map(PessoaDTO::new)
                .orElseThrow(() -> new ExceptionEntidadeNaoEncontrada(String.format("Não encontrada Pessoa com id: %d.")));
    }

    @Override
    public List<PessoaDTO> findAll() {

        return this.repository.findAll()
                .stream()
                .map(PessoaDTO::new)
                .sorted(Comparator.comparing(PessoaDTO::id).reversed())
                .toList();
    }
}
