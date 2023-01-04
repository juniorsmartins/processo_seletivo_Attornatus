package attornatus.cliente.presentation.controllers;

import attornatus.cliente.presentation.dtos.PolicyDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public sealed abstract class PolicyPessoaController<T extends PolicyDTO<ID>, ID> permits PessoaController {

    @PostMapping
    public abstract ResponseEntity<T> create(T dto, UriComponentsBuilder uriComponentsBuilder);

    @PutMapping(path = "/{id}")
    public abstract ResponseEntity<T> update(T dto);

    @GetMapping(path = "/{id}")
    public abstract ResponseEntity<T> findById(ID id);

    @GetMapping
    public abstract ResponseEntity<List<T>> findAll();
}

