package it.digiulio.social71.repository;

import it.digiulio.social71.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserByIdAndActiveIsTrue (Long id);
    Optional<User> findUserByUsernameAndActiveIsTrue (String username);
    Optional<User> findUserByEmailAndActiveIsTrue (String email);
}
