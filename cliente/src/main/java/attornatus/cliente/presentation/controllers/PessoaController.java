package attornatus.cliente.presentation.controllers;

import attornatus.cliente.business.services.PolicyPessoaService;
import attornatus.cliente.presentation.dtos.PessoaDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value = "v1/pessoas", produces = {"application/json"})
public final class PessoaController extends PolicyPessoaController<PessoaDTO, Long> {

    @Autowired
    private PolicyPessoaService<PessoaDTO, Long> service;

    @Override
    public ResponseEntity<PessoaDTO> create(@RequestBody @Valid PessoaDTO dto, UriComponentsBuilder uriComponentsBuilder) {

        var pessoaSalva = this.service.create(dto);

        return ResponseEntity
                .created(uriComponentsBuilder
                        .path("v1/pessoas/{id}")
                        .buildAndExpand(pessoaSalva.id())
                        .toUri())
                .body(pessoaSalva);
    }

    @Override
    public ResponseEntity<PessoaDTO> update(@PathVariable(name = "id") Long id, @RequestBody @Valid PessoaDTO dto) {

        var pessoaAtualizada = this.service.update(id, dto);

        return ResponseEntity
                .ok()
                .body(pessoaAtualizada);
    }

    @Override
    public ResponseEntity<PessoaDTO> findById(@PathVariable(name = "id") Long id) {

        var pessoaEncontrada = this.service.findById(id);

        return ResponseEntity
                .ok()
                .body(pessoaEncontrada);
    }

    @Override
    public ResponseEntity<List<PessoaDTO>> findAll() {

        var pessoas = this.service.findAll();

        if(pessoas.isEmpty())
            return ResponseEntity
                    .noContent()
                    .build();

        return ResponseEntity
                .ok()
                .body(pessoas);
    }
}
