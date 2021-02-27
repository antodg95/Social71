package it.digiulio.social71.repository;

import it.digiulio.social71.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
