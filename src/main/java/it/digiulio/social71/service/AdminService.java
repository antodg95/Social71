package it.digiulio.social71.service;

import it.digiulio.social71.models.Authority;
import it.digiulio.social71.models.User;
import it.digiulio.social71.repository.AuthorityRepository;
import it.digiulio.social71.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class AdminService {

    private final AuthorityRepository authorityRepository;

    public User grantAdminAuthoritiesToUser(User user) {
        Authority authority = new Authority();
        authority.setAuthority("ROLE_ADMIN");
        authority.setUser(user);
        authorityRepository.save(authority);
        return user;
    }
}
