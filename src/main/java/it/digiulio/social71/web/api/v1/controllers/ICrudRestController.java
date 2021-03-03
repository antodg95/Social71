package it.digiulio.social71.web.api.v1.controllers;

import it.digiulio.social71.exception.BadServiceRequestException;
import it.digiulio.social71.exception.ValidationException;
import org.springframework.web.bind.annotation.*;

public interface ICrudRestController<T> {
    @PostMapping
    T create(@RequestBody T entity)
            throws ValidationException, BadServiceRequestException;

    @GetMapping("/{id}")
    T findById(@PathVariable(name = "id") Long id) throws ValidationException;

    @PutMapping("/{id}")
    T update(@PathVariable(name = "id") Long id, @RequestBody T entity)
            throws BadServiceRequestException, ValidationException;

    @DeleteMapping("/{id}")
    T delete(@PathVariable(name = "id") Long id)
            throws BadServiceRequestException;
}
