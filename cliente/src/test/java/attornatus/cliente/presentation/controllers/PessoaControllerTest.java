package attornatus.cliente.presentation.controllers;

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

//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.catchThrowable;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class PessoaControllerTest {

    private final String LOGRADOURO = "Avenida Uncle Bob";
    private final String CEP = "78.000-000";
    private final int NUMERO = 1550;
    private final TipoEnderecoEnum TIPO1 = TipoEnderecoEnum.PRINCIPAL;
    private final TipoEnderecoEnum TIPO2 = TipoEnderecoEnum.SECUNDARIO;
    private final String NOME = "Robert Cecil Martin";
    private final LocalDate DATA_NASCIMENTO = LocalDate.of(1952, 12, 05);

    private UriComponentsBuilder uriComponentsBuilder;
    private EnderecoDTO enderecoDTO;
    private EnderecoDTO enderecoDTO1;
    private EnderecoDTO enderecoDTO2;
    private PessoaDTO pessoaDTO1;
    private PessoaDTO pessoaDTO2;
    private PessoaDTO pessoaDTO3;

    @Autowired
    private PolicyPessoaController<PessoaDTO, Long> controller;

    @Autowired
    private PessoaRepositoryJPA pessoaRepositoryJPA;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeEach
    void criadorDeCenarios() {
        uriComponentsBuilder = UriComponentsBuilder.newInstance();

        enderecoDTO = new EnderecoDTO(null, LOGRADOURO, CEP, NUMERO, TIPO1);
        enderecoDTO1 = new EnderecoDTO(null, LOGRADOURO, CEP, NUMERO, TIPO2);
        pessoaDTO1 = new PessoaDTO(null, NOME, DATA_NASCIMENTO, List.of(enderecoDTO, enderecoDTO1));

        pessoaDTO2 = new PessoaDTO(null, NOME, null, List.of(enderecoDTO1));

        enderecoDTO2 = new EnderecoDTO(null, LOGRADOURO, null, NUMERO, TIPO1);
        pessoaDTO3 = new PessoaDTO(null, NOME, DATA_NASCIMENTO, List.of(enderecoDTO2));
    }

    @Test
    @DisplayName("Fluxo Principal - Caminho feliz.")
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


