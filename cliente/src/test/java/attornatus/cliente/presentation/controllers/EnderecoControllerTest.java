package attornatus.cliente.presentation.controllers;

import attornatus.cliente.business.entities.EnderecoEntity;
import attornatus.cliente.business.entities.PessoaEntity;
import attornatus.cliente.business.entities.TipoEnderecoEnum;
import attornatus.cliente.persistence.EnderecoRepositoryJPA;
import attornatus.cliente.persistence.PessoaRepositoryJPA;
import attornatus.cliente.presentation.dtos.EnderecoDTO;
import attornatus.cliente.presentation.dtos.PessoaDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class EnderecoControllerTest {

    private final String LOGRADOURO = "Avenida Uncle Bob";
    private final String CEP = "78.000-000";
    private final int NUMERO = 1550;
    private final TipoEnderecoEnum TIPO1 = TipoEnderecoEnum.PRINCIPAL;
    private final TipoEnderecoEnum TIPO2 = TipoEnderecoEnum.SECUNDARIO;
    private final String NOME = "Robert Cecil Martin";
    private final LocalDate DATA_NASCIMENTO = LocalDate.of(1952, 12, 05);

    private EnderecoDTO enderecoDTO1;
    private EnderecoEntity enderecoEntity1;
    private PessoaEntity pessoaEntity1;

    private UriComponentsBuilder uriComponentsBuilder;

    @Autowired
    private PolicyEnderecoController<EnderecoDTO, Long> controller;

    @Autowired
    private EnderecoRepositoryJPA enderecoRepositoryJPA;

    @Autowired
    private PessoaRepositoryJPA pessoaRepositoryJPA;

    @BeforeEach
    void criadorDeCenarios() {
        uriComponentsBuilder = UriComponentsBuilder.newInstance();

        enderecoEntity1 = EnderecoEntity.builder()
                .logradouro(LOGRADOURO)
                .cep(CEP)
                .numero(NUMERO)
                .tipo(TIPO1)
                .build();

        pessoaEntity1 = PessoaEntity.builder()
                .nome(NOME)
                .dataNascimento(DATA_NASCIMENTO)
                .enderecos(List.of(enderecoEntity1))
                .build();

        enderecoDTO1 = new EnderecoDTO(null, LOGRADOURO, CEP, NUMERO, TIPO2);
    }

    @Test
    @DisplayName("Create - Fluxo Principal")
    void create_returnResponseEntityDeEnderecoDTOComHttp201() {
        var pessoaSalva = this.pessoaRepositoryJPA.save(pessoaEntity1);
        var pessoaId = pessoaSalva.getId();

        var response = this.controller.create(pessoaId, enderecoDTO1, uriComponentsBuilder);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(EnderecoDTO.class, response.getBody().getClass());

        Assertions.assertEquals(LOGRADOURO, response.getBody().logradouro());
        Assertions.assertEquals(CEP, response.getBody().cep());
        Assertions.assertEquals(NUMERO, response.getBody().numero());

        this.pessoaRepositoryJPA.deleteById(pessoaId);
    }

    @Test
    @DisplayName("FindAll - Fluxo Principal")
    void findAll_returnResponseEntityDeListaDeEnderecosDTOComHttp200() {
        var pessoaSalva = this.pessoaRepositoryJPA.save(pessoaEntity1);
        var response = this.controller.findAll();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Assertions.assertNotNull(response.getBody().get(0).getClass());
        Assertions.assertEquals(EnderecoDTO.class, response.getBody().get(0).getClass());

        this.pessoaRepositoryJPA.deleteById(pessoaSalva.getId());
    }
}