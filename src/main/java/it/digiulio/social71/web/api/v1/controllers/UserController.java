package it.digiulio.social71.web.api.v1.controllers;

import it.digiulio.social71.exception.BadServiceRequestException;
import it.digiulio.social71.exception.NotFoundException;
import it.digiulio.social71.exception.ValidationException;
import it.digiulio.social71.models.User;
import it.digiulio.social71.service.UserService;
import it.digiulio.social71.web.api.v1.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1/users")
public class UserController implements ICrudRestController<UserDTO>{

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    public UserDTO create(UserDTO userDTO){
        log.debug("POST: api/v1/users - create");
        log.trace("user: {}", userDTO);

        User user = this.modelMapper.map(userDTO, User.class);

        user = this.userService.create(user);

        return this.modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO findById(Long id) throws ValidationException {
        log.debug("GET: api/v1/users/{id} - findById ");
        log.trace("id: {}", id);
        if (id < 0) {
            throw new BadServiceRequestException("Id", id.toString(), List.of("must be greater than 0"));
        }

        Optional<User> user = this.userService.findById(id);

        if (user.isEmpty()) {
            throw new NotFoundException(id.toString(), "User");
        }

        return this.modelMapper.map(user.get(), UserDTO.class);
    }

    @Override
    public UserDTO update(Long id, UserDTO userDTO) throws BadServiceRequestException, ValidationException {
        log.debug("PUT: api/v1/users/{id} - update");
        log.trace("id: {}, user:{}", id, userDTO);

        if (id < 0) {
            throw new BadServiceRequestException("Id", id.toString(), List.of("must be greater than 0"));
        }

        userDTO.setId(id);
        User user = this.modelMapper.map(userDTO, User.class);
        user = this.userService.update(user);

        log.trace("updated user: {}", user);
        return this.modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO delete(Long id) throws BadServiceRequestException {
        log.debug("DELETE: api/v1/users/{id} - delete");
        log.trace("id: {}", id);

        if (id < 0) {
            throw new BadServiceRequestException("Id", id.toString(), List.of("must be greater than 0"));
        }

        User user = this.userService.delete(id);

        log.trace("deleted user: {}", user);
        return this.modelMapper.map(user, UserDTO.class);
    }
}
