package attornatus.cliente.business.services;

import attornatus.cliente.business.entities.EnderecoEntity;
import attornatus.cliente.business.entities.PessoaEntity;
import attornatus.cliente.business.entities.TipoEnderecoEnum;
import attornatus.cliente.business.exceptions.ExceptionEntidadeNaoEncontrada;
import attornatus.cliente.business.exceptions.ExceptionTipoEnderecoPrincipalUnico;
import attornatus.cliente.business.exceptions.MensagemPadrao;
import attornatus.cliente.business.ports.PolicyPessoaRepository;
import attornatus.cliente.persistence.PessoaRepositoryJPA;
import attornatus.cliente.presentation.dtos.EnderecoDTO;
import attornatus.cliente.presentation.dtos.PessoaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class PessoaServiceTest {

    private final Long ID = 500L;
    private final String LOGRADOURO1 = "Rua A";
    private final String LOGRADOURO2 = "Rua B";
    private final String CEP1 = "99.999-999";
    private final String CEP2 = "11.111-111";
    private final int NUMERO1 = 100;
    private final int NUMERO2 = 200;
    private final TipoEnderecoEnum TIPO1 = TipoEnderecoEnum.PRINCIPAL;
    private final TipoEnderecoEnum TIPO2 = TipoEnderecoEnum.SECUNDARIO;
    private final String CIDADE1 = "Belém";
    private final String CIDADE2 = "Roma";

    private final String NOME1 = "Robert Schildt";
    private final LocalDate DATA_NASCIMENTO1 = LocalDate.of(2002, 10, 2);

    private PessoaDTO pessoaDTO1;
    private PessoaDTO pessoaDTO2;
    private PessoaEntity pessoaEntity1;

    @Autowired
    private PessoaService service;

    @MockBean
    private PolicyPessoaRepository<PessoaEntity, Long> repository;

    @BeforeEach
    void criadorDeCenarios() {

        var enderecoDTO1 = new EnderecoDTO(null, LOGRADOURO1, CEP1, NUMERO1, TIPO1, CIDADE1);
        var enderecoDTO2 = new EnderecoDTO(null, LOGRADOURO2, CEP2, NUMERO2, TIPO1, CIDADE2);
        pessoaDTO1 = new PessoaDTO(null, NOME1, DATA_NASCIMENTO1, List.of(enderecoDTO1, enderecoDTO2));

        var enderecoDTO3 = new EnderecoDTO(1L, LOGRADOURO1, CEP1, NUMERO1, TIPO1, CIDADE1);
        var enderecoDTO4 = new EnderecoDTO(2L, LOGRADOURO2, CEP2, NUMERO2, TIPO1, CIDADE2);
        pessoaDTO2 = new PessoaDTO(null, NOME1, DATA_NASCIMENTO1, List.of(enderecoDTO3, enderecoDTO4));

        var endereco1 = EnderecoEntity.builder()
                .id(1L)
                .logradouro(LOGRADOURO1)
                .cep(CEP1)
                .numero(NUMERO1)
                .tipo(TIPO1)
                .cidade(CIDADE1)
                .build();

        var endereco2 = EnderecoEntity.builder()
                .id(2L)
                .logradouro(LOGRADOURO2)
                .cep(CEP2)
                .numero(NUMERO2)
                .tipo(TIPO2)
                .cidade(CIDADE2)
                .build();

        pessoaEntity1 = PessoaEntity.builder()
                .id(1L)
                .nome(NOME1)
                .dataNascimento(DATA_NASCIMENTO1)
                .enderecos(List.of(endereco1, endereco2))
                .build();
    }

    @Test
    @DisplayName("Create - Fluxo Exception")
    void create_ExceptionTipoEnderecoPrincipalUnico() {

        Throwable thrown = org.assertj.core.api.Assertions.catchThrowable(() ->
            this.service.create(pessoaDTO1)
        );

        org.assertj.core.api.Assertions.assertThat(thrown)
                .isInstanceOf(ExceptionTipoEnderecoPrincipalUnico.class)
                .hasMessage(MensagemPadrao.TIPO_ENDERECO_PRINCIPAL_UNICO);

    }

    @Test
    @DisplayName("Update - Fluxo Exception I")
    void update_ExceptionTipoEnderecoPrincipalUnico() {
        Mockito.when(this.repository.findById(Mockito.anyLong())).thenReturn(Optional.of(pessoaEntity1));

        Throwable thrown = org.assertj.core.api.Assertions.catchThrowable(() ->
                this.service.update(1L, pessoaDTO2)
        );

        org.assertj.core.api.Assertions.assertThat(thrown)
                .isInstanceOf(ExceptionTipoEnderecoPrincipalUnico.class)
                .hasMessage(MensagemPadrao.TIPO_ENDERECO_PRINCIPAL_UNICO);

        Mockito.verify(this.repository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    @DisplayName("Update - Fluxo Exception II")
    void update() {
        Mockito.when(this.repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Throwable thrown = org.assertj.core.api.Assertions.catchThrowable(() ->
                this.service.update(ID, pessoaDTO2)
        );

        org.assertj.core.api.Assertions.assertThat(thrown)
                .isInstanceOf(ExceptionEntidadeNaoEncontrada.class)
                .hasMessage(MensagemPadrao.PESSOA_NAO_ENCONTRADA);

        Mockito.verify(this.repository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    @DisplayName("")
    void findById() {
    }

    @Test
    @DisplayName("")
    void findAll() {
    }
}