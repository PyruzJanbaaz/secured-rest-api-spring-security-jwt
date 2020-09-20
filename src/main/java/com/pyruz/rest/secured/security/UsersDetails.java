package com.pyruz.rest.secured.security;

import com.pyruz.rest.secured.configuration.ApplicationContextHolder;
import com.pyruz.rest.secured.configuration.ApplicationProperties;
import com.pyruz.rest.secured.exception.ServiceException;
import com.pyruz.rest.secured.model.entity.User;
import com.pyruz.rest.secured.repository.MenuRepository;
import com.pyruz.rest.secured.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsersDetails extends ApplicationContextHolder implements UserDetailsService {

    @Autowired private ApplicationProperties applicationProperties;
    @Autowired private UserRepository userRepository;
    @Autowired private MenuRepository menuRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findUserByUsernameIgnoreCaseAndIsActiveIsTrue(username);
        if (!user.isPresent()) {
            throw ServiceException.builder()
                    .message(applicationProperties.getProperty("application.message.invalidLogin.text"))
                    .httpStatus(HttpStatus.UNAUTHORIZED).build();
        }
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        List<String> result = menuRepository.findUserRoles(user.get().getId());
        result.forEach(role -> simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role)));

        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.get().getPassword())
                .authorities(simpleGrantedAuthorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!user.get().getIsActive())
                .build();
    }

}
