package attornatus.cliente.presentation.controllers;

import attornatus.cliente.persistence.PessoaRepositoryJPA;
import attornatus.cliente.presentation.dtos.EnderecoDTO;
import attornatus.cliente.presentation.dtos.PessoaDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.util.UriComponentsBuilder;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

@SpringBootTest
class PessoaControllerTest {

    private final String LOGRADOURO = "Avenida Uncle Bob";
    private final String CEP = "78.000-000";
    private final int NUMERO = 1550;
    private final String NOME = "Robert Cecil Martin";
    private final LocalDate DATA_NASCIMENTO = LocalDate.of(1952, 12, 05);

    private UriComponentsBuilder uriComponentsBuilder;
    private EnderecoDTO enderecoDTO1;
    private EnderecoDTO enderecoDTO2;
    private PessoaDTO pessoaDTO1;
    private PessoaDTO pessoaDTO2;
    private PessoaDTO pessoaDTO3;

    @Autowired
    private PolicyController<PessoaDTO, Long> controller;

    @Autowired
    private PessoaRepositoryJPA pessoaRepositoryJPA;

    @BeforeEach
    void criadorDeCenarios() {
        uriComponentsBuilder = UriComponentsBuilder.newInstance();

        enderecoDTO1 = new EnderecoDTO(null, LOGRADOURO, CEP, NUMERO);
        pessoaDTO1 = new PessoaDTO(null, NOME, DATA_NASCIMENTO, enderecoDTO1);

        pessoaDTO2 = new PessoaDTO(null, NOME, null, enderecoDTO1);

        enderecoDTO2 = new EnderecoDTO(null, LOGRADOURO, null, NUMERO);
        pessoaDTO3 = new PessoaDTO(null, NOME, DATA_NASCIMENTO, enderecoDTO2);
    }

    @Test
    void create_returnResponseEntityDePessoaDTOComHttp201() {
        var response = this.controller.create(pessoaDTO1, uriComponentsBuilder);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(PessoaDTO.class, response.getBody().getClass());

        Assertions.assertEquals(LOGRADOURO, response.getBody().endereco().logradouro());
        Assertions.assertEquals(CEP, response.getBody().endereco().cep());
        Assertions.assertEquals(NUMERO, response.getBody().endereco().numero());
        Assertions.assertEquals(NOME, response.getBody().nome());
        Assertions.assertEquals(DATA_NASCIMENTO, response.getBody().dataNascimento());

        this.pessoaRepositoryJPA.deleteById(response.getBody().id());
    }

    @Test
    void create_returnResponseEntityDeErroPadraoComHttp404() {

        Throwable response = catchThrowable(() -> {
            this.controller.create(pessoaDTO2, uriComponentsBuilder);
        });

        assertThat(response).isInstanceOf(MethodArgumentNotValidException.class);
    }
}


