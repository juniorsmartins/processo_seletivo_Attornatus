package attornatus.cliente.presentation.controllers;

import attornatus.cliente.presentation.dtos.PolicyDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public interface PolicyController<T extends PolicyDTO<ID>, ID> {

    @PostMapping
    ResponseEntity<T> create(T dto, UriComponentsBuilder uriComponentsBuilder);

    @PutMapping(path = "/{id}")
    ResponseEntity<T> update(T dto);

    @GetMapping(path = "/{id}")
    ResponseEntity<T> find(ID id);

    @GetMapping
    ResponseEntity<List<T>> findAll();
}
