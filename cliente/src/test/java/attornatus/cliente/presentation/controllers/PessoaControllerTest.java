package attornatus.cliente.presentation.controllers;

import attornatus.cliente.business.entities.EnderecoEntity;
import attornatus.cliente.business.entities.PessoaEntity;
import attornatus.cliente.business.entities.TipoEnderecoEnum;
import attornatus.cliente.persistence.PessoaRepositoryJPA;
import attornatus.cliente.presentation.dtos.EnderecoDTO;
import attornatus.cliente.presentation.dtos.PessoaDTO;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class PessoaControllerTest {

    private final String LOGRADOURO = "Avenida Uncle Bob";
    private final String CEP = "78.000-000";
    private final int NUMERO = 1550;
    private final TipoEnderecoEnum TIPO1 = TipoEnderecoEnum.PRINCIPAL;
    private final TipoEnderecoEnum TIPO2 = TipoEnderecoEnum.SECUNDARIO;
    private final String CIDADE = "Nova York";
    private final String NOME = "Robert Cecil Martin";
    private final LocalDate DATA_NASCIMENTO = LocalDate.of(1952, 12, 05);

    private UriComponentsBuilder uriComponentsBuilder;
    private EnderecoDTO enderecoDTO;
    private EnderecoDTO enderecoDTO1;
    private PessoaDTO pessoaDTO1;
    private PessoaEntity pessoaEntity1;

    @Autowired
    private PolicyPessoaController<PessoaDTO, Long> controller;

    @Autowired
    private PessoaRepositoryJPA pessoaRepositoryJPA;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeEach
    void criadorDeCenarios() {
        uriComponentsBuilder = UriComponentsBuilder.newInstance();

        var enderecoEntity1 = EnderecoEntity.builder()
                .logradouro(LOGRADOURO)
                .cep(CEP)
                .numero(NUMERO)
                .tipo(TIPO1)
                .cidade(CIDADE)
                .build();

        pessoaEntity1 = PessoaEntity.builder()
                .nome(NOME)
                .dataNascimento(DATA_NASCIMENTO)
                .enderecos(List.of(enderecoEntity1))
                .build();

        enderecoDTO = new EnderecoDTO(null, LOGRADOURO, CEP, NUMERO, TIPO1, CIDADE);
        enderecoDTO1 = new EnderecoDTO(null, LOGRADOURO, CEP, NUMERO, TIPO2, CIDADE);
        pessoaDTO1 = new PessoaDTO(null, NOME, DATA_NASCIMENTO, List.of(enderecoDTO, enderecoDTO1));
    }

    @Test
    @DisplayName("Create - Fluxo Principal")
    void create_returnResponseEntityDePessoaDTOComHttp201() {
        var response = this.controller.create(pessoaDTO1, uriComponentsBuilder);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(PessoaDTO.class, response.getBody().getClass());

        Assertions.assertEquals(LOGRADOURO, response.getBody().enderecos().get(0).logradouro());
        Assertions.assertEquals(CEP, response.getBody().enderecos().get(0).cep());
        Assertions.assertEquals(NUMERO, response.getBody().enderecos().get(0).numero());
        Assertions.assertEquals(NOME, response.getBody().nome());
        Assertions.assertEquals(DATA_NASCIMENTO, response.getBody().dataNascimento());

        this.pessoaRepositoryJPA.deleteById(response.getBody().id());
    }

    @Test
    @DisplayName("FindAll - Fluxo Principal")
    void findAll_returnResponseEntityDeListaDePessoasDTOComHttp200() {
        var pessoaSalva = this.controller.create(pessoaDTO1, uriComponentsBuilder);
        var response = this.controller.findAll();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Assertions.assertNotNull(response.getBody().get(0).getClass());
        Assertions.assertEquals(PessoaDTO.class, response.getBody().get(0).getClass());

        this.pessoaRepositoryJPA.deleteById(pessoaSalva.getBody().id());
    }

    @Test
    @DisplayName("FindById - Fluxo Principal")
    void find_returnResponseEntityDePessoaDTOComHttp200() {
        var pessoaSalva = this.pessoaRepositoryJPA.save(pessoaEntity1);
        var response = this.controller.findById(pessoaSalva.getId());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(PessoaDTO.class, response.getBody().getClass());
    }

//    @Test
//    @DisplayName("Fluxo de Exception - Testa Bean Validation em Pessoa.")
//    void create_returnResponseEntityDeErroPadraoComHttp400() {
//
//        thrown.expect(MethodArgumentNotValidException.class);
//        this.controller.create(pessoaDTO2, uriComponentsBuilder);
//    }

//    @Test
//    @DisplayName("Fluxo de Exception - Testa Bean Validation em Endereço.")
//    void create3_returnResponseEntityDeErroPadraoComHttp400() {
//
//        Throwable response = org.assertj.core.api.Assertions.catchThrowable(() -> {
//            this.controller.create(pessoaDTO3, uriComponentsBuilder);
//        });
//
//        org.assertj.core.api.Assertions.assertThat(response).isInstanceOf(MethodArgumentNotValidException.class);
//    }
}


