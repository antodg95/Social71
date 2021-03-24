package it.digiulio.social71.web.api.v1.controllers;

import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.digiulio.social71.exception.AuthorizationException;
import it.digiulio.social71.exception.BadServiceRequestException;
import it.digiulio.social71.exception.NotFoundException;
import it.digiulio.social71.exception.ValidationException;
import it.digiulio.social71.models.User;
import it.digiulio.social71.service.UserService;
import it.digiulio.social71.web.api.v1.dto.request.UserDTORequest;
import it.digiulio.social71.web.api.v1.dto.response.UserDTOResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1/users")
public class UserController implements ICrudRestController<UserDTORequest, UserDTOResponse>{

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    @Operation(summary = "Create User", tags = {"User"}, responses = {
            @ApiResponse(responseCode = "200", description = "The User", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDTOResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Service Request Exception or Validation Exception")
    })
    @Timed(value = "user.create", description = "Time spent creating users", percentiles = {0.50, 0.75, 0.95, 0.98, 0.99, 0.999})
    public UserDTOResponse create(
            @RequestBody(description = "User that needs to be created", required = true) UserDTORequest userDTO
    ) throws BadServiceRequestException, ValidationException {
        log.debug("POST: api/v1/users - create");
        log.trace("user: {}", userDTO);

        User user = this.modelMapper.map(userDTO, User.class);

        user = this.userService.create(user);

        return this.modelMapper.map(user, UserDTOResponse.class);
    }

    @Override
    @Operation(summary = "Find User by ID", tags = {"User"}, responses = {
            @ApiResponse(responseCode = "200", description = "The User", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDTOResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Service Request, id must be greater than 0"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @Timed(value = "user.findById", description = "Time spent finding users by id", percentiles = {0.50, 0.75, 0.95, 0.98, 0.99, 0.999})
    public UserDTOResponse findById(
            @Parameter(description = "User's Id that need to be fetched. Must be > 0", required = true) Long id
    ) throws BadServiceRequestException, NotFoundException {
        log.debug("GET: api/v1/users/{id} - findById ");
        log.trace("id: {}", id);
        if (id < 0) {
            throw new BadServiceRequestException("Id", id.toString(), List.of("must be greater than 0"));
        }

        Optional<User> user = this.userService.findById(id);

        if (user.isEmpty()) {
            throw new NotFoundException(id.toString(), "User");
        }

        return this.modelMapper.map(user.get(), UserDTOResponse.class);
    }

    @Override
    @Operation(summary = "Update User", tags = {"User"}, responses = {
            @ApiResponse(responseCode = "200", description = "The User", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDTOResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Service Request Exception or Validation Exception."),
            @ApiResponse(responseCode = "403", description = "AuthorizationException"),
    })
    @Timed(value = "user.update", description = "Time spent updating users", percentiles = {0.50, 0.75, 0.95, 0.98, 0.99, 0.999})
    public UserDTOResponse update(
            @Parameter(description = "User's Id that need to be updated. Must be > 0", required = true) Long id,
            @RequestBody(description = "User's fields that need to be updated", required = true) UserDTORequest userDTO
    ) throws BadServiceRequestException, ValidationException, AuthorizationException {
        log.debug("PUT: api/v1/users/{id} - update");
        log.trace("id: {}, user:{}", id, userDTO);

        if (id < 0) {
            throw new BadServiceRequestException("Id", id.toString(), List.of("must be greater than 0"));
        }

        User user = this.modelMapper.map(userDTO, User.class);
        user.setId(id);
        user = this.userService.update(user);

        log.trace("updated user: {}", user);
        return this.modelMapper.map(user, UserDTOResponse.class);
    }

    @Override
    @Operation(summary = "Delete User", tags = {"User"}, responses = {
            @ApiResponse(responseCode = "200", description = "The User", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDTOResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Service Request Exception: User doesn't exist or Id < 0"),
            @ApiResponse(responseCode = "403", description = "AuthorizationException"),
    })
    @Timed(value = "user.delete", description = "Time spent deleting users", percentiles = {0.50, 0.75, 0.95, 0.98, 0.99, 0.999})
    public UserDTOResponse delete(
            @Parameter(description = "User's Id that need to be deleted. Must be > 0", required = true) Long id
    ) throws BadServiceRequestException, AuthorizationException {
        log.debug("DELETE: api/v1/users/{id} - delete");
        log.trace("id: {}", id);

        if (id < 0) {
            throw new BadServiceRequestException("Id", id.toString(), List.of("must be greater than 0"));
        }

        User user = this.userService.delete(id);

        log.trace("deleted user: {}", user);
        return this.modelMapper.map(user, UserDTOResponse.class);
    }

    @GetMapping
    @Operation(summary = "List all Users", tags = {"User"}, responses = {
            @ApiResponse(responseCode = "200", description = "List of all Users", content = @Content(mediaType = "application/json",
                    schema = @Schema(allOf = UserDTOResponse.class)))
    })
    @Timed(value = "user.findAll", description = "Time spent listing all users", percentiles = {0.50, 0.75, 0.95, 0.98, 0.99, 0.999})
    public ResponseEntity<Map<String, Object>> findAll() {
        log.debug("GET: api/v1/users - findAll");

        List<User> userList = this.userService.findAll();
        List<UserDTOResponse> userDTOResponseList = userList
                .stream()
                .map(user -> this.modelMapper.map(user, UserDTOResponse.class))
                .collect(Collectors.toList());
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("resultSize", userDTOResponseList.size());
        returnMap.put("users", userDTOResponseList);
        return ResponseEntity.ok(returnMap);
    }
}
