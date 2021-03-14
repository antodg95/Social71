package it.digiulio.social71.repository;

import it.digiulio.social71.models.User;
import it.digiulio.social71.models.Whisper;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface WhisperRepository extends CrudRepository<Whisper, Long> {
    List<Whisper> findAllByUserAndActiveIsTrue(User user);
    Optional<Whisper> findWhisperByIdAndActiveIsTrue(Long id);
}
