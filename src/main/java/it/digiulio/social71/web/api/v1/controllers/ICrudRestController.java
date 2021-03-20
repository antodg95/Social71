package it.digiulio.social71.web.api.v1.controllers;

import it.digiulio.social71.exception.BadServiceRequestException;
import it.digiulio.social71.exception.ValidationException;
import org.springframework.web.bind.annotation.*;

/**
 * Interface to generalize standard Crud Rest endpoints
 * @param <U> Request DTO class
 * @param <T> Response DTO class
 */
public interface ICrudRestController<U, T> {
    @PostMapping
    T create(@RequestBody U entity)
            throws ValidationException, BadServiceRequestException;

    @GetMapping("/{id}")
    T findById(@PathVariable(name = "id") Long id) throws ValidationException;

    @PutMapping("/{id}")
    T update(@PathVariable(name = "id") Long id, @RequestBody U entity)
            throws BadServiceRequestException, ValidationException;

    @DeleteMapping("/{id}")
    T delete(@PathVariable(name = "id") Long id)
            throws BadServiceRequestException;
}
