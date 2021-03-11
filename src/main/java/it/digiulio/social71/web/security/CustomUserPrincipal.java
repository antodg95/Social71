package it.digiulio.social71.web.security;

import it.digiulio.social71.models.Authority;
import it.digiulio.social71.models.User;
import it.digiulio.social71.repository.AuthorityRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class CustomUserPrincipal implements UserDetails {
    private static final long serialVersionUID = -6555903587473659559L;

    private final User user;
    private final AuthorityRepository authorityRepository;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Iterable<Authority> authorities = authorityRepository.findAllByUser(user);
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authorities.forEach(authority -> authorityList.add(new SimpleGrantedAuthority(authority.getAuthority())));
        return authorityList;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getActive();
    }
}
