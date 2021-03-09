package it.digiulio.social71.repository;

import it.digiulio.social71.models.Authority;
import it.digiulio.social71.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Long> {
    Iterable<Authority> findAllByUser (User user);
}
