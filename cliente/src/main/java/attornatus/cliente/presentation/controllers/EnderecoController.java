package attornatus.cliente.presentation.controllers;

import attornatus.cliente.presentation.dtos.EnderecoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value = "v1/enderecos", produces = {"application/json"})
public final class EnderecoController extends PolicyController<EnderecoDTO, Long> {

    @Override
    public ResponseEntity<EnderecoDTO> create(EnderecoDTO dto, UriComponentsBuilder uriComponentsBuilder) {
        return null;
    }

    @Override
    public ResponseEntity<EnderecoDTO> update(EnderecoDTO dto) {
        return null;
    }

    @Override
    public ResponseEntity<EnderecoDTO> find(Long aLong) {
        return null;
    }

    @Override
    public ResponseEntity<List<EnderecoDTO>> findAll() {
        return null;
    }
}
