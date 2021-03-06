package it.digiulio.social71;

import it.digiulio.social71.models.User;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class TestUtils {
    public static List<User> createTestUsers(int n) {
        List<User> userList = new ArrayList<>();

        for (int i = 0; i<n; i++) {
            User user = new User();;
            user.setUsername("username-" + i);
            user.setEmail("test-" + i + "@test.com");
            user.setPassword("password-" + i);
            user.setId((long) i);
            user.setActive(true);
            userList.add(user);
        }

        return userList;
    }
}
