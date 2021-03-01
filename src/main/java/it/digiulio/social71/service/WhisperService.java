package it.digiulio.social71.service;

import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import it.digiulio.social71.models.Whisper;
import it.digiulio.social71.repository.WhisperRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor
public class WhisperService implements ICrudService<Whisper>{

    private final WhisperRepository whisperRepository;

    @Override
    public Whisper create(Whisper entity) {

        return null;
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
    public Whisper update(Whisper entity) {
        return null;
    }

    @Override
    public Whisper delete(Long id) {
        return null;
    }
}
