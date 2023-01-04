package attornatus.cliente.presentation.controllers;

import attornatus.cliente.presentation.dtos.PolicyDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public sealed abstract class PolicyEnderecoController<T extends PolicyDTO<ID>, ID> permits EnderecoController {

    @PostMapping(path = "/{pessoaId}")
    public abstract ResponseEntity<T> create(ID pessoaId, T dto, UriComponentsBuilder uriComponentsBuilder);

    @GetMapping
    public abstract ResponseEntity<List<T>> findAll();
}

