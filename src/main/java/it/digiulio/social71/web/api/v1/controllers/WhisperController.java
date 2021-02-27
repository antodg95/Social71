package it.digiulio.social71.web.api.v1.controllers;

import it.digiulio.social71.models.User;
import it.digiulio.social71.models.Whisper;
import it.digiulio.social71.repository.UserRepository;
import it.digiulio.social71.repository.WhisperRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/whispers")
public class WhisperController {

    private final WhisperRepository whisperRepository;
    private final UserRepository userRepository;

    @GetMapping("/{userId}")
    public Set<Whisper> getWhispersByUserId(@PathVariable Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            Set<Whisper> whispers = whisperRepository.findAllByUserId(optionalUser.get());
            return whispers;
        }
        return null;
    }
}
