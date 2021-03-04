package it.digiulio.social71.service;

import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import it.digiulio.social71.exception.BadServiceRequestException;
import it.digiulio.social71.exception.ValidationException;
import it.digiulio.social71.models.User;
import it.digiulio.social71.models.Whisper;
import it.digiulio.social71.repository.UserRepository;
import it.digiulio.social71.repository.WhisperRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class WhisperService implements ICrudService<Whisper>{

    private final WhisperRepository whisperRepository;
    private final UserRepository userRepository;

    @Override
    public Whisper create(Whisper whisper) throws ValidationException {

        if (checkWhisperValidationConstraint(whisper, true)) {
            log.debug("whisper in input is constraint ok");
        }

        Optional<User> optionalUser = userRepository.findUserByIdAndActiveIsTrue(whisper.getUser().getId());

        if (optionalUser.isEmpty()) {
            throw new BadServiceRequestException("User with id", whisper.getUser().getId().toString(), List.of("doesn't exists"));
        }

        whisper.setCreatedOn(Timestamp.from(Instant.now()));
        whisper.setActive(true);

        return whisperRepository.save(whisper);
    }

    @Override
    public Set<Whisper> findAll() {
        Iterator<Whisper> userIterator = whisperRepository.findAll().iterator();
        return Sets.newHashSet(Iterators.filter(userIterator, Whisper.class));
    }

    @Override
    public Optional<Whisper> findById(Long id) {
        return whisperRepository.findById(id);
    }

    @Override
    public Whisper update(Whisper whisper) {
        if (checkWhisperValidationConstraint(whisper, false)) {
            log.debug("whisper in input is constraint ok");
        }

        Optional<User> optionalUser = userRepository.findUserByIdAndActiveIsTrue(whisper.getUser().getId());

        if (optionalUser.isEmpty()) {
            throw new BadServiceRequestException("User", whisper.getUser().getId().toString(), List.of("doesn't exist"));
        }

        return whisperRepository.save(whisper);

    }

    @Override
    public Whisper delete(Long id) {
        Optional<Whisper> optionalWhisper = whisperRepository.findById(id);

        if (optionalWhisper.isEmpty()) {
            throw new BadServiceRequestException("Id", id.toString(), List.of("doesn't exist"));
        }

        Whisper whisper = optionalWhisper.get();
        whisper.setActive(false);
        whisperRepository.save(whisper);
        return optionalWhisper.get();
    }


    private boolean checkWhisperValidationConstraint(Whisper whisper, boolean id) throws ValidationException{
        if (id) {
            if (whisper.getId() != null) {
                throw new ValidationException("Id", whisper.getId().toString(), List.of("must be null"));
            }
        }

        if (whisper.getText() == null || whisper.getText().length() == 0) {
            throw new ValidationException("Text", whisper.getText(), List.of("must be not null or lenght greater than 0"));
        }

        if (whisper.getUser() == null) {
            throw new ValidationException("User", null, List.of("must be not null"));
        }

        return true;
    }
}
