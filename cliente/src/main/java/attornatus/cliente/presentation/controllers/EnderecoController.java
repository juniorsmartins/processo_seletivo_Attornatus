package attornatus.cliente.presentation.controllers;

import attornatus.cliente.business.services.PolicyEnderecoService;
import attornatus.cliente.presentation.dtos.EnderecoDTO;
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
@RequestMapping(value = "v1/enderecos", produces = {"application/json"})
public final class EnderecoController extends PolicyEnderecoController<EnderecoDTO, Long> {

    @Autowired
    private PolicyEnderecoService<EnderecoDTO, Long> enderecoService;

    @Override
    public ResponseEntity<EnderecoDTO> create(@PathVariable(name = "pessoaId") Long pessoaId, @RequestBody @Valid EnderecoDTO dto, UriComponentsBuilder uriComponentsBuilder) {
        var endereco = this.enderecoService.create(pessoaId, dto);

        return ResponseEntity
                .created(uriComponentsBuilder
                        .path("v1/enderecos/{id}")
                        .buildAndExpand(endereco.id())
                        .toUri())
                .body(endereco);
    }

    @Override
    public ResponseEntity<List<EnderecoDTO>> findAll() {

        var enderecos = this.enderecoService.findAll();

        if(enderecos.isEmpty())
            return ResponseEntity
                    .noContent()
                    .build();

        return ResponseEntity
                .ok()
                .body(enderecos);
    }
}
