package it.digiulio.social71.web.controllers;

import static org.assertj.core.api.Assertions.*;

import it.digiulio.social71.TestUtils;
import it.digiulio.social71.configuration.JacksonConfiguration;
import it.digiulio.social71.exception.BadServiceRequestException;
import it.digiulio.social71.models.User;
import it.digiulio.social71.service.UserService;
import it.digiulio.social71.web.api.v1.controllers.UserController;
import it.digiulio.social71.web.api.v1.dto.UserDTO;
import it.digiulio.social71.web.api.v1.mappings.UserDtoMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContext;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class UserControllerTest {

    @Mock
    private UserService userService;

    private UserController userController;
    private List<User> userList;
    private Long id;

    @BeforeEach
    public void setUp() {
        int defaultListSize = 20;
        this.userList = TestUtils.createTestUsers(defaultListSize);
        this.id= (long) defaultListSize - 1;
        ModelMapper mm = new JacksonConfiguration(List.of(new UserDtoMapper())).modelMapper();
        this.userController = new UserController(this.userService, mm);

        Mockito.lenient().when(userService.create(Mockito.any(User.class)))
                .thenAnswer( parameter -> {
                    User user = parameter.getArgument(0);

                    if (userList.stream().anyMatch(user1 -> user1.getUsername().equals(user.getUsername()) && user1.getActive())) {
                        throw new BadServiceRequestException("Username", user.getUsername(), List.of("already exists"));
                    }

                    user.setId(++id);
                    user.setCreatedOn(Timestamp.from(Instant.now()));
                    user.setActive(true);
                    this.userList.add(user);
                    return user;
                });
    }

    @Test
    public void testCreateWithAllValidInput() {
        UserDTO userDto = new UserDTO();
        userDto.setUsername("username-test");
        userDto.setEmail("test@test.com");
        userDto.setPassword("password");

        UserDTO ret = userController.create(userDto);

        assertThat(ret.getId()).isNotNull();
        assertThat(ret.getPassword()).isEqualTo(userDto.getPassword());
        assertThat(ret.getEmail()).isEqualTo(userDto.getEmail());
        assertThat(ret.getUsername()).isEqualTo(userDto.getUsername());
    }

    @Test
    public void testCreateWithExistingUsername() {
        UserDTO userDto = new UserDTO();
        userDto.setUsername("username-3");
        userDto.setEmail("test@test");
        userDto.setPassword("password");

        assertThatExceptionOfType(BadServiceRequestException.class)
                .isThrownBy(() -> userController.create(userDto))
                .withMessageContaining("Username")
                .withMessageContaining("already exists");
    }



}
