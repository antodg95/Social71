package it.digiulio.social71.service;

import static org.assertj.core.api.Assertions.*;

import it.digiulio.social71.TestUtils;
import it.digiulio.social71.exception.BadServiceRequestException;
import it.digiulio.social71.exception.ValidationException;
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
import java.util.*;

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
        int defaultListSize = 20;
        this.userList = TestUtils.createTestUsers(defaultListSize);
        this.id= (long) defaultListSize - 1;

        Mockito.lenient().when(this.userRepository.save(Mockito.any(User.class)))
                .thenAnswer( parameter -> {
                    User user = parameter.getArgument(0);
                    user.setId(++id);
                    user.setCreatedOn(Timestamp.from(Instant.now()));
                    user.setActive(true);
                    this.userList.add(user);
                    return user;
                } );

        Mockito.lenient().when(this.userRepository.findUserByUsernameAndActiveIsTrue(Mockito.anyString()))
                .thenAnswer( parameter -> {
                    String username = parameter.getArgument(0);
                    return userList.stream().filter(user -> user.getUsername().equals(username) && user.getActive().equals(true)).findFirst();
                });

        Mockito.lenient().when(this.userRepository.findUserByEmailAndActiveIsTrue(Mockito.anyString()))
                .thenAnswer( parameter -> {
                    String email = parameter.getArgument(0);
                    return userList.stream().filter(user -> user.getEmail().equals(email) && user.getActive().equals(true)).findFirst();
                });

        Mockito.lenient().when(this.userRepository.findAll())
                .thenReturn(new HashSet<>(userList));

        Mockito.lenient().when(this.userRepository.findById(Mockito.anyLong()))
                .thenAnswer( parameter -> {
                    Long id = parameter.getArgument(0);
                    return userList.stream().filter(user -> user.getId().equals(id)).findFirst();
                });
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
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getActive()).isTrue();

        Mockito.verify(this.userRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void testCreateWithWrongEmail() {
        User user = new User();
        user.setUsername("username-test");
        user.setEmail("test@test");
        user.setPassword("password");

        assertThatExceptionOfType(BadServiceRequestException.class)
                .isThrownBy( () -> userService.create(user))
                .withMessageContaining("Email");
    }

    @Test
    public void testCreateWithNullUsername() {
        User user = new User();
        user.setUsername(null);
        user.setEmail("test@test");
        user.setPassword("password");

        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy( () -> userService.create(user))
                .withMessageContaining("Username");
    }

    @Test
    public void testCreateWithExplicitId() {
        User user = new User();
        user.setUsername("username-test");
        user.setEmail("test@test");
        user.setPassword("password");
        user.setId(71L);

        user.setId(71L);
        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy( () -> userService.create(user))
                .withMessageContaining("Id");
    }

    @Test
    public void testCreateWithUsernameAlreadyExisting() {
        User user = new User();
        user.setPassword("password");
        user.setEmail("test@test.com");
        user.setUsername("username-4");

        assertThatExceptionOfType(BadServiceRequestException.class)
                .isThrownBy(() -> userService.create(user))
                .withMessageContaining("Username")
                .withMessageContaining("already exists");
    }

    @Test
    public void testCreateWithEmailAlreadyExisting() {
        User user = new User();
        user.setPassword("password");
        user.setEmail("test-7@test.com");
        user.setUsername("username-test");

        assertThatExceptionOfType(BadServiceRequestException.class)
                .isThrownBy(() -> userService.create(user))
                .withMessageContaining("Email")
                .withMessageContaining("already exists");
    }

    @Test
    public void testFindAll() {
        Set<User> users = userService.findAll();

        assertThat(users.size()).isEqualTo(userList.size());
    }

    @Test
    public void testFindByExistingId() {
        Optional<User> optionalUser = userService.findById(7L);

        assertThat(optionalUser).isPresent();
        assertThat(optionalUser.get().getId()).isEqualTo(7L);
        assertThat(optionalUser.get().getUsername()).isEqualTo("username-7");
    }

    @Test
    public void testFindByNonExistingId() {
        Optional<User> optionalUser = userService.findById(91L);

        assertThat(optionalUser).isEmpty();
    }
}
