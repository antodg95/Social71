package it.digiulio.social71.web.api.v1.controllers;

import it.digiulio.social71.exception.BadServiceRequestException;
import it.digiulio.social71.exception.ValidationException;
import it.digiulio.social71.models.User;
import it.digiulio.social71.repository.UserRepository;
import it.digiulio.social71.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1/users")
public class UserController implements ICrudRestController<User>{

    private final UserService userService;

    @Override
    public User create(User entity) throws ValidationException, BadServiceRequestException {
        return null;
    }

    @Override
    public User findById(Integer id) throws ValidationException {
        return null;
    }

    @Override
    public User update(Integer id, User entity) throws BadServiceRequestException, ValidationException {
        return null;
    }

    @Override
    public User delete(Integer id) throws BadServiceRequestException {
        return null;
    }
}
