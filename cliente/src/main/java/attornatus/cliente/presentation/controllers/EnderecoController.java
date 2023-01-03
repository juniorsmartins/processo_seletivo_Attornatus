package attornatus.cliente.presentation.controllers;

import attornatus.cliente.presentation.dtos.EnderecoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public class EnderecoController implements PolicyController<EnderecoDTO, Long> {

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
