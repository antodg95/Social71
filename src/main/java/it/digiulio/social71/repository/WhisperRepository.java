package it.digiulio.social71.repository;

import it.digiulio.social71.models.User;
import it.digiulio.social71.models.Whisper;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface WhisperRepository extends CrudRepository<Whisper, Long> {
    Set<Whisper> findAllByUserId(User userId);
}
