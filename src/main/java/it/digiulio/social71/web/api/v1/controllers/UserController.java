package it.digiulio.social71.web.api.v1.controllers;

import it.digiulio.social71.models.User;
import it.digiulio.social71.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        log.debug("getUserById() with id:{}", id);
        return userRepository.findById(id).orElse(null);
    }
}
