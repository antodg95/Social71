package it.digiulio.social71.web.dto;

import it.digiulio.social71.configuration.JacksonConfiguration;
import it.digiulio.social71.models.User;
import it.digiulio.social71.web.api.v1.dto.request.UserDTORequest;
import it.digiulio.social71.web.api.v1.dto.response.UserDTOResponse;
import it.digiulio.social71.web.api.v1.mappings.UserDtoMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class UserDtoTest {

    private ModelMapper mm;

    private User user;

    @BeforeEach
    public void setUp() {
        this.mm = new JacksonConfiguration(List.of(new UserDtoMapper())).modelMapper();
        this.user = new User();
        user.setId(1L);
        user.setUsername("username-test");
        user.setPassword("password-test");
        user.setEmail("email@test.com");
        user.setCreatedOn(Timestamp.from(Instant.now()));
        user.setActive(true);
    }

    @Test
    public void validateToDtoResponse() {
        this.mm.typeMap(User.class, UserDTOResponse.class).validate();
    }

    @Test
    public void validateFromDtoRequest() {
        this.mm.typeMap(UserDTORequest.class, User.class).validate();
    }

    @Test
    public void testToDtoResponse() {
        UserDTOResponse userDTO = this.mm.map(user, UserDTOResponse.class);

        assertThat(userDTO.getUsername()).isEqualTo(user.getUsername());
        assertThat(userDTO.getEmail()).isEqualTo(user.getEmail());
        assertThat(userDTO.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    public void testFromDtoRequest() {
        UserDTORequest userDTO = this.mm.map(user, UserDTORequest.class);
        User user1 = this.mm.map(userDTO, User.class);

        assertThat(user1.getUsername()).isEqualTo(user.getUsername());
        assertThat(user1.getEmail()).isEqualTo(user.getEmail());
        assertThat(user1.getPassword()).isEqualTo(user.getPassword());
        assertThat(user1.getActive()).isNull();
        assertThat(user1.getCreatedOn()).isNull();
        assertThat(user1.getDeleteUuid()).isNull();
    }

}
