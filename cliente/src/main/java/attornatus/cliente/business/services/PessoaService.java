package attornatus.cliente.business.services;

import attornatus.cliente.business.entities.PessoaEntity;
import attornatus.cliente.business.entities.TipoEnderecoEnum;
import attornatus.cliente.business.exceptions.ExceptionEntidadeNaoEncontrada;
import attornatus.cliente.business.exceptions.ExceptionTipoEnderecoPrincipalUnico;
import attornatus.cliente.business.exceptions.MensagemPadrao;
import attornatus.cliente.business.ports.PolicyPessoaRepository;
import attornatus.cliente.presentation.dtos.PessoaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public non-sealed class PessoaService implements PolicyPessoaService<PessoaDTO, Long> {

    @Autowired
    private PolicyPessoaRepository<PessoaEntity, Long> repository;

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    @Override
    public PessoaDTO create(PessoaDTO dto) {

        return Optional.of(dto)
                .map(PessoaEntity::new)
                .map(pessoaNova -> {
                    validacaoDeRegraDeTipoPrincipalUnico(pessoaNova);
                    pessoaNova.getEnderecos().forEach(endereco -> endereco.setPessoa(pessoaNova));
                    return this.repository.save(pessoaNova);
                })
                .map(PessoaDTO::new)
                .orElseThrow();
    }

    private void validacaoDeRegraDeTipoPrincipalUnico(PessoaEntity entity) {

        if(entity.getEnderecos().stream()
                        .filter(endereco -> endereco.getTipo().equals(TipoEnderecoEnum.PRINCIPAL))
                        .toList()
                        .size() > 1)
            throw new ExceptionTipoEnderecoPrincipalUnico(MensagemPadrao.TIPO_ENDERECO_PRINCIPAL_UNICO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    @Override
    public PessoaDTO update(Long id, PessoaDTO dto) {

        return this.repository.findById(id)
                .map(pessoaDatabase -> {

                    pessoaDatabase.setNome(dto.nome());
                    pessoaDatabase.setDataNascimento(dto.dataNascimento());
                    pessoaDatabase.getEnderecos().forEach(endereco -> {
                        dto.enderecos().forEach(enderecoNovo -> {
                            if(endereco.getId() == enderecoNovo.id()) {
                                endereco.setLogradouro(enderecoNovo.logradouro());
                                endereco.setCep(enderecoNovo.cep());
                                endereco.setNumero(enderecoNovo.numero());
                                endereco.setTipo(enderecoNovo.tipo());
                                endereco.setCidade(enderecoNovo.cidade());
                            }
                        });
                    });
                    validacaoDeRegraDeTipoPrincipalUnico(pessoaDatabase);
                    return pessoaDatabase;
                })
                .map(PessoaDTO::new)
                .orElseThrow(() -> new ExceptionEntidadeNaoEncontrada(MensagemPadrao.PESSOA_NAO_ENCONTRADA));
    }

    @Override
    public PessoaDTO findById(Long id) {

        return this.repository.findById(id)
                .map(PessoaDTO::new)
                .orElseThrow(() -> new ExceptionEntidadeNaoEncontrada(String.format("Não encontrada Pessoa com id: %d.", id)));
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
