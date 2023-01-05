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

    private final String LOGRADOURO1 = "Avenida Uncle Bob";
    private final String LOGRADOURO2 = "Rua Ane Frank";
    private final String CEP1 = "78.000-000";
    private final String CEP2 = "80.800-800";
    private final int NUMERO1 = 1550;
    private final int NUMERO2 = 5555;
    private final TipoEnderecoEnum TIPO1 = TipoEnderecoEnum.PRINCIPAL;
    private final TipoEnderecoEnum TIPO2 = TipoEnderecoEnum.SECUNDARIO;
    private final String CIDADE1 = "Nova York";
    private final String CIDADE2 = "Istambul";
    private final String NOME1 = "Robert Cecil Martin";
    private final String NOME2 = "Jeff Sutherland";
    private final LocalDate DATA_NASCIMENTO1 = LocalDate.of(1952, 12, 5);
    private final LocalDate DATA_NASCIMENTO2 = LocalDate.of(1985, 8, 15);

    private UriComponentsBuilder uriComponentsBuilder;
    private PessoaDTO pessoaDTO1;
    private PessoaDTO pessoaDTO2;
    private PessoaEntity pessoaEntity1;

    @Autowired
    private PolicyPessoaController<PessoaDTO, Long> controller;

    @Autowired
    private PessoaRepositoryJPA pessoaRepositoryJPA;

    @BeforeEach
    void criadorDeCenarios() {
        uriComponentsBuilder = UriComponentsBuilder.newInstance();

        var enderecoEntity1 = EnderecoEntity.builder()
                .logradouro(LOGRADOURO1)
                .cep(CEP1)
                .numero(NUMERO1)
                .tipo(TIPO1)
                .cidade(CIDADE1)
                .build();

        pessoaEntity1 = PessoaEntity.builder()
                .nome(NOME1)
                .dataNascimento(DATA_NASCIMENTO1)
                .enderecos(List.of(enderecoEntity1))
                .build();

        var enderecoDTO1 = new EnderecoDTO(null, LOGRADOURO1, CEP1, NUMERO1, TIPO1, CIDADE1);
        var enderecoDTO2 = new EnderecoDTO(null, LOGRADOURO2, CEP2, NUMERO2, TIPO2, CIDADE2);
        pessoaDTO1 = new PessoaDTO(null, NOME1, DATA_NASCIMENTO1, List.of(enderecoDTO1, enderecoDTO2));

        pessoaDTO2 = new PessoaDTO(null, NOME2, DATA_NASCIMENTO2, List.of(enderecoDTO2));
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

        Assertions.assertEquals(LOGRADOURO1, response.getBody().enderecos().get(0).logradouro());
        Assertions.assertEquals(CEP1, response.getBody().enderecos().get(0).cep());
        Assertions.assertEquals(NUMERO1, response.getBody().enderecos().get(0).numero());
        Assertions.assertEquals(NOME1, response.getBody().nome());
        Assertions.assertEquals(DATA_NASCIMENTO1, response.getBody().dataNascimento());

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

    @Test
    @DisplayName("Update - Fluxo Principal")
    void update_returnResponseEntityDePessoaDTOComHttp200() {
        var pessoaSalva = this.pessoaRepositoryJPA.save(pessoaEntity1);
        var response = this.controller.update(pessoaSalva.getId(), pessoaDTO2);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(PessoaDTO.class, response.getBody().getClass());

        Assertions.assertNotEquals(NOME1, NOME2);
        Assertions.assertNotEquals(pessoaSalva.getNome(), response.getBody().nome());
        Assertions.assertNotEquals(DATA_NASCIMENTO1, DATA_NASCIMENTO2);
        Assertions.assertNotEquals(pessoaSalva.getDataNascimento(), response.getBody().dataNascimento());
    }
}


