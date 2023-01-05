package attornatus.cliente.business.services;

import attornatus.cliente.business.entities.EnderecoEntity;
import attornatus.cliente.business.entities.PessoaEntity;
import attornatus.cliente.business.entities.TipoEnderecoEnum;
import attornatus.cliente.business.exceptions.ExceptionEntidadeNaoEncontrada;
import attornatus.cliente.business.exceptions.ExceptionRequisicaoMalFeita;
import attornatus.cliente.business.exceptions.ExceptionTipoEnderecoPrincipalUnico;
import attornatus.cliente.business.ports.PolicyEnderecoRepository;
import attornatus.cliente.business.ports.PolicyPessoaRepository;
import attornatus.cliente.presentation.dtos.EnderecoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public non-sealed class EnderecoService implements PolicyEnderecoService<EnderecoDTO, Long> {

    @Autowired
    private PolicyEnderecoRepository<EnderecoEntity, Long> enderecoRepository;

    @Autowired
    private PolicyPessoaRepository<PessoaEntity, Long> pessoaRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    @Override
    public EnderecoDTO create(Long pessoaId, EnderecoDTO dto) {
        var pessoa = this.pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new ExceptionRequisicaoMalFeita(String.format("Não encontrada Pessoa com id: %d.", pessoaId)));

        return Optional.of(dto)
                .map(EnderecoEntity::new)
                .map(enderecoNovo -> {
                    pessoa.getEnderecos().add(enderecoNovo);
                    enderecoNovo.setPessoa(pessoa);
                    enderecoNovo = this.enderecoRepository.save(enderecoNovo);
                    validacaoDeRegraDeTipoPrincipalUnico(pessoa);
                    return enderecoNovo;
                })
                .map(EnderecoDTO::new)
                .orElseThrow();
    }

    private void validacaoDeRegraDeTipoPrincipalUnico(PessoaEntity entity) {

        if(entity.getEnderecos().stream()
                .filter(endereco -> endereco.getTipo().equals(TipoEnderecoEnum.PRINCIPAL))
                .toList()
                .size() > 1)
            throw new ExceptionTipoEnderecoPrincipalUnico("Permitido apenas um endereço principal.");
    }

    @Override
    public List<EnderecoDTO> findAll() {

        return this.enderecoRepository.findAll()
                .stream()
                .map(EnderecoDTO::new)
                .sorted(Comparator.comparing(EnderecoDTO::id).reversed())
                .toList();
    }
}
