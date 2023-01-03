package attornatus.cliente.presentation.controllers;

import attornatus.cliente.business.services.PessoaService;
import attornatus.cliente.business.services.PolicyService;
import attornatus.cliente.presentation.dtos.PessoaDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value = "v1/pessoas", produces = {"application/json"})
public final class PessoaController extends PolicyController<PessoaDTO, Long> {

    @Autowired
    private PolicyService<PessoaDTO, Long> service;

    @Override
    public ResponseEntity<PessoaDTO> create(@RequestBody @Valid PessoaDTO dto, UriComponentsBuilder uriComponentsBuilder) {
        var pessoa = this.service.create(dto);

        return ResponseEntity
                .created(uriComponentsBuilder
                        .path("v1/pessoas/{id}")
                        .buildAndExpand(pessoa.id())
                        .toUri())
                .body(pessoa);
    }

    @Override
    public ResponseEntity<PessoaDTO> update(PessoaDTO dto) {
        return null;
    }

    @Override
    public ResponseEntity<PessoaDTO> find(Long aLong) {
        return null;
    }

    @Override
    public ResponseEntity<List<PessoaDTO>> findAll() {
        return null;
    }
}
