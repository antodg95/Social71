package it.digiulio.social71.service;

import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import it.digiulio.social71.exception.AuthorizationException;
import it.digiulio.social71.exception.BadServiceRequestException;
import it.digiulio.social71.exception.ValidationException;
import it.digiulio.social71.models.Authority;
import it.digiulio.social71.models.User;
import it.digiulio.social71.repository.AuthorityRepository;
import it.digiulio.social71.repository.UserRepository;
import it.digiulio.social71.utils.AuthenticationUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class UserService implements ICrudService<User>{

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder encoder;

    @Override
    public User create(User user) throws ValidationException, BadServiceRequestException{
        log.debug("create()");
        log.trace("user: {}", user.toString());

        if (checkUserValidationConstraint(user, true)) {
            log.debug("user in input is constraint ok");
        }
        if (checkUserBadServiceRequest(user)) {
            log.debug("User in input is valid for request");
        }

        //TODO: Check per la password. Vedere come gestirla correttamente.
        user.setPassword(encoder.encode(user.getPassword()));

        user.setCreatedOn(Timestamp.from(Instant.now()));
        user.setActive(true);

        user = userRepository.save(user);

        Authority authority = new Authority();
        authority.setAuthority("ROLE_USER");
        authority.setUser(user);
        authorityRepository.save(authority);

        return user;
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
    public User update(User user) throws ValidationException, BadServiceRequestException, AuthorizationException{
        log.debug("update()");
        log.trace("user: {}", user.toString());

        if (authorizationCheck(user.getId())){
            log.debug("authorizationCheck OK!");
        }

        Optional<User> optionalUser = userRepository.findUserByIdAndActiveIsTrue(user.getId());

        if (optionalUser.isEmpty()) {
            throw new BadServiceRequestException("User", user.getId().toString(), List.of("doesn't exist"));
        }

        User userFound = optionalUser.get();

        if (user.getUsername() != null && !user.getUsername().equals(userFound.getUsername())) {
            Optional<User> optionalUserByUsername = userRepository.findUserByUsernameAndActiveIsTrue(user.getUsername());
            if (optionalUserByUsername.isPresent()) {
                throw new BadServiceRequestException("Username", user.getUsername(), List.of("already exists"));
            }
        }
        userFound.setUsername(user.getUsername() == null ? userFound.getUsername() : user.getUsername());

        if (user.getEmail() != null && !user.getEmail().equals(userFound.getEmail())) {
            if (!EmailValidator.getInstance().isValid(user.getEmail())) {
                throw new BadServiceRequestException("Email", user.getEmail(), List.of("must be a valid email address"));
            }

            Optional<User> optionalUserByEmail = userRepository.findUserByEmailAndActiveIsTrue(user.getEmail());
            if (optionalUserByEmail.isPresent()){
                throw new BadServiceRequestException("Email", user.getEmail(), List.of("already exists"));
            }
        }
        userFound.setEmail(user.getEmail() == null ? userFound.getEmail() : user.getEmail());

        return userRepository.save(userFound);
    }

    @Override
    public User delete(Long id) throws BadServiceRequestException, AuthorizationException {
        log.debug("delete()");
        log.trace("user id: {}", id.toString());

        if (authorizationCheck(id)){
            log.debug("authorizationCheck OK!");
        }

        Optional<User> optionalUser = userRepository.findUserByIdAndActiveIsTrue(id);
        if (optionalUser.isEmpty()) {
            throw new BadServiceRequestException("Id", id.toString(), List.of("doesn't exist"));
        }

        User user = optionalUser.get();
        UUID uuid = UUID.randomUUID();
        user.setActive(false);
        user.setDeleteUuid(uuid.toString());

        return userRepository.save(user);

    }

    /**
     * Check if the user in input is constraint ok.
     *
     * @param user User
     * @param id True means the methods checks if the id is != null. False otherwise.
     * @return true or throws ValidationException
     * @throws ValidationException if user in input is constraint K.O.
     */
    private boolean checkUserValidationConstraint(User user, boolean id) throws ValidationException{
        if (id) {
            if (user.getId() != null) {
                throw new ValidationException("Id", user.getId().toString(), List.of("must be null"));
            }
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

        if (!EmailValidator.getInstance().isValid(user.getEmail())) {
            throw new BadServiceRequestException("Email", user.getEmail(), List.of("must be a valid email address"));
        }

        Optional<User> optionalUserByEmail = userRepository.findUserByEmailAndActiveIsTrue(user.getEmail());
        if (optionalUserByEmail.isPresent()){
            throw new BadServiceRequestException("Email", user.getEmail(), List.of("already exists"));
        }

        return true;
    }

    private boolean authorizationCheck(Long id) throws AuthorizationException{
        Optional<User> optionalLoggedUser = userRepository.findUserByUsernameAndActiveIsTrue(AuthenticationUtils.getCurrentLoggedUsername());
        if (optionalLoggedUser.isPresent()) {
            User loggedUser = optionalLoggedUser.get();
            if (!loggedUser.getId().equals(id) && !AuthenticationUtils.getCurrentUserAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                throw new AuthorizationException();
            }
        }
        return true;
    }
}
