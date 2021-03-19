package it.digiulio.social71.service;

import it.digiulio.social71.exception.AuthorizationException;
import it.digiulio.social71.exception.BadServiceRequestException;
import it.digiulio.social71.exception.ValidationException;

import java.util.List;
import java.util.Optional;

public interface ICrudService<T> {
    T create(T entity) throws ValidationException, BadServiceRequestException;

    List<T> findAll();

    Optional<T> findById(Long id);

    T update(T entity) throws ValidationException, BadServiceRequestException, AuthorizationException;

    T delete(Long id) throws BadServiceRequestException, AuthorizationException;
}
