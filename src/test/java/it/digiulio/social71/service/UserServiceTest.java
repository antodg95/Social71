package it.digiulio.social71.service;

import static org.assertj.core.api.Assertions.*;

import it.digiulio.social71.models.User;
import it.digiulio.social71.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;
    private List<User> userList;
    private Long id;

    @BeforeEach
    public void setUp() {
        this.userService = new UserService(userRepository);
        this.userList = new ArrayList<>();
        this.id=0L;

        Mockito.when(this.userRepository.save(Mockito.any(User.class)))
                .thenAnswer( parameter -> {
                    User user = parameter.getArgument(0);
                    user.setId(++id);
                    user.setCreatedOn(Timestamp.from(Instant.now()));
                    this.userList.add(user);
                    return user;
                } );

    }

    @Test
    @DisplayName("Tests create method with all valid input data")
    public void testCreateWithAllValidInput() {
        User user = new User();
        user.setUsername("username-test");
        user.setEmail("test@test.com");
        user.setPassword("password");

        User result = userService.create(user);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().ignoringFields("id", "createdOn", "active").isEqualTo(user);
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getActive()).isTrue();

        Mockito.verify(this.userRepository, Mockito.times(1)).save(user);
    }

    //@Test
    public void testCreateWithWrongInput() {
        User user = new User();
        user.setUsername("username-test");
        user.setEmail("antodg95@gmail.com");
        user.setPassword("password");
    }
}
