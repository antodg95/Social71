package it.digiulio.social71.service;

import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import it.digiulio.social71.exception.BadServiceRequestException;
import it.digiulio.social71.exception.ValidationException;
import it.digiulio.social71.models.User;
import it.digiulio.social71.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class UserService implements ICrudService<User>{

    private final UserRepository userRepository;

    @Override
    public User create(User user) {
        log.debug("create()");
        log.trace("\tuser: {}", user.toString());

        if (checkUserValidationConstraint(user)) {
            log.debug("user in input is constraint ok");
        }
        if (checkUserBadServiceRequest(user)) {
            log.debug("User in input is valid for request");
        }

        //TODO: Check per la password. Vedere come gestirla correttamente.

        user.setActive(true);

        return userRepository.save(user);
    }

    @Override
    public Set<User> findAll() {
        Iterator<User> userIterator = userRepository.findAll().iterator();
        return Sets.newHashSet(Iterators.filter(userIterator, User.class));
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User update(User user) {
        log.debug("update()");
        log.trace("\tuser: {}", user.toString());

        if (checkUserValidationConstraint(user)) {
            log.debug("user in input is constraint ok");
        }
        if (checkUserBadServiceRequest(user)) {
            log.debug("User in input is valid for request");
        }

        return userRepository.save(user);
    }

    @Override
    public User delete(Long id) {
        log.debug("delete()");
        log.trace("\tuser id: {}", id.toString());

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            throw new BadServiceRequestException("Id", id.toString(), List.of("doesn't exist"));
        }

        User user = optionalUser.get();
        UUID uuid = UUID.randomUUID();
        user.setActive(false);
        user.setDeleteUuid(uuid.toString());

        return userRepository.save(user);

    }

    private boolean checkUserValidationConstraint(User user) throws ValidationException{
        if (user.getId() != null) {
            throw new ValidationException("Id", user.getId().toString(), List.of("must be null"));
        }

        if (user.getUsername() == null) {
            throw new ValidationException("Username", user.getUsername(), List.of("must be not null"));
        }

        if (user.getEmail() == null) {
            throw new ValidationException("Email", user.getEmail(), List.of("must be not null"));
        }

        if (user.getPassword() == null) {
            throw new ValidationException("Password", user.getPassword(), List.of("must be not null"));
        }

        return true;
    }

    private boolean checkUserBadServiceRequest(User user) throws BadServiceRequestException {

        Optional<User> optionalUserByUsername = userRepository.findUserByUsernameAndActiveIsTrue(user.getUsername());
        if (optionalUserByUsername.isPresent()) {
            throw new BadServiceRequestException("Username", user.getUsername(), List.of("already exists"));
        }

        if (!EmailValidator.getInstance(true, true).isValid(user.getEmail())) {
            throw new BadServiceRequestException("Email", user.getEmail(), List.of("must be a valid email address"));
        }

        Optional<User> optionalUserByEmail = userRepository.findUserByEmailAndActiveIsTrue(user.getEmail());
        if (optionalUserByEmail.isPresent()){
            throw new BadServiceRequestException("Email", user.getEmail(), List.of("already exists"));
        }

        return true;
    }
}
