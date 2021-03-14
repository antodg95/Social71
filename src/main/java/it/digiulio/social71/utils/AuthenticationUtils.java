package it.digiulio.social71.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

@UtilityClass
public class AuthenticationUtils {
    public static String getCurrentLoggedUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static Collection<? extends GrantedAuthority> getCurrentUserAuthorities() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }
}
