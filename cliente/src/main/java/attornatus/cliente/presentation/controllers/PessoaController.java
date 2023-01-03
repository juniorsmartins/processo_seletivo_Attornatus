package attornatus.cliente.presentation.controllers;

import attornatus.cliente.presentation.dtos.PessoaDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value = "v1/pessoas", produces = {"application/json"})
public final class PessoaController extends PolicyController<PessoaDTO, Long> {

    @Override
    public ResponseEntity<PessoaDTO> create(PessoaDTO dto, UriComponentsBuilder uriComponentsBuilder) {
        return null;
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
