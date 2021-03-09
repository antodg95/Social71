package it.digiulio.social71.web.api.v1.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.digiulio.social71.exception.BadServiceRequestException;
import it.digiulio.social71.exception.NotFoundException;
import it.digiulio.social71.exception.ValidationException;
import it.digiulio.social71.models.User;
import it.digiulio.social71.service.UserService;
import it.digiulio.social71.web.api.v1.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Operation(summary = "Create User", tags = {"User"}, responses = {
            @ApiResponse(responseCode = "200", description = "The User", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Service Request Exception or Validation Exception")
    })
    public UserDTO create(
            @RequestBody(description = "User that needs to be created", required = true) UserDTO userDTO
    ) throws BadServiceRequestException, ValidationException {
        log.debug("POST: api/v1/users - create");
        log.trace("user: {}", userDTO);

        User user = this.modelMapper.map(userDTO, User.class);

        user = this.userService.create(user);

        return this.modelMapper.map(user, UserDTO.class);
    }

    @Override
    @Operation(summary = "Find User by ID", tags = {"User"}, responses = {
            @ApiResponse(responseCode = "200", description = "The User", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Service Request, id must be greater than 0"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public UserDTO findById(
            @Parameter(description = "User's Id that need to be fetched. Must be > 0", required = true) Long id
    ) throws BadServiceRequestException, NotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.debug(auth.getName());
        log.debug(auth.getAuthorities().toString());
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
    @Operation(summary = "Update User", tags = {"User"}, responses = {
            @ApiResponse(responseCode = "200", description = "The User", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Service Request Exception or Validation Exception."),
    })
    public UserDTO update(
            @Parameter(description = "User's Id that need to be updated. Must be > 0", required = true) Long id,
            @RequestBody(description = "User's fields that need to be updated", required = true) UserDTO userDTO
    ) throws BadServiceRequestException, ValidationException {
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
    @Operation(summary = "Delete User", tags = {"User"}, responses = {
            @ApiResponse(responseCode = "200", description = "The User", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Service Request Exception: User doesn't exist or Id < 0"),
    })
    public UserDTO delete(
            @Parameter(description = "User's Id that need to be deleted. Must be > 0", required = true) Long id
    ) throws BadServiceRequestException {
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
