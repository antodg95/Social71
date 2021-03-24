package it.digiulio.social71.web.api.v1.controllers;

import it.digiulio.social71.exception.BadServiceRequestException;
import it.digiulio.social71.exception.ValidationException;
import it.digiulio.social71.models.User;
import it.digiulio.social71.service.AdminService;
import it.digiulio.social71.service.UserService;
import it.digiulio.social71.web.api.v1.dto.request.UserDTORequest;
import it.digiulio.social71.web.api.v1.dto.response.UserDTOResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping("/user")
    public UserDTOResponse createAdminUser (
            @RequestBody UserDTORequest userDTO
    ) throws BadServiceRequestException, ValidationException {
        log.debug("POST: api/v1/users - create");
        log.trace("user: {}", userDTO);

        User user = this.modelMapper.map(userDTO, User.class);
        user = this.userService.create(user);
        user = this.adminService.grantAdminAuthoritiesToUser(user);
        return modelMapper.map(user, UserDTOResponse.class);
    }
}
