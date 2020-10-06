package com.pyruz.rest.secured.security;

import com.pyruz.rest.secured.configuration.ApplicationProperties;
import com.pyruz.rest.secured.exception.ServiceException;
import com.pyruz.rest.secured.model.entity.User;
import com.pyruz.rest.secured.repository.ApiRepository;
import com.pyruz.rest.secured.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsersDetails implements UserDetailsService {

    final ApplicationProperties applicationProperties;
    final UserRepository userRepository;
    final ApiRepository apiRepository;


    public UsersDetails(ApplicationProperties applicationProperties, UserRepository userRepository, ApiRepository apiRepository) {
        this.applicationProperties = applicationProperties;
        this.userRepository = userRepository;
        this.apiRepository = apiRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findUserByUsernameIgnoreCaseAndIsActiveIsTrue(username);
        if (!user.isPresent()) {
            throw ServiceException.builder()
                    .message(applicationProperties.getProperty("application.message.invalidLogin.text"))
                    .httpStatus(HttpStatus.UNAUTHORIZED).build();
        }
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        List<String> result = apiRepository.findUserRoles(user.get().getId());
        result.forEach(role -> {
            if (!role.trim().equals(""))
                simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role));
        });

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
