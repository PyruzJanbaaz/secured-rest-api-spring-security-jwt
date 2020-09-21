package com.pyruz.rest.secured.service;


import com.pyruz.rest.secured.configuration.ApplicationContextHolder;
import com.pyruz.rest.secured.exception.ServiceException;
import com.pyruz.rest.secured.model.domain.AddNewUserBean;
import com.pyruz.rest.secured.model.domain.ChangePasswordBean;
import com.pyruz.rest.secured.model.domain.ResetPasswordBean;
import com.pyruz.rest.secured.model.domain.UpdateUserBean;
import com.pyruz.rest.secured.model.dto.BaseDTO;
import com.pyruz.rest.secured.model.dto.MetaDTO;
import com.pyruz.rest.secured.model.dto.PageDTO;
import com.pyruz.rest.secured.model.dto.UserDTO;
import com.pyruz.rest.secured.model.entity.Access;
import com.pyruz.rest.secured.model.entity.User;
import com.pyruz.rest.secured.model.mapper.UserMapper;
import com.pyruz.rest.secured.repository.AccessRepository;
import com.pyruz.rest.secured.repository.UserRepository;
import com.pyruz.rest.secured.security.JwtTokenProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService extends ApplicationContextHolder {

    final PasswordEncoder passwordEncoder;
    final JwtTokenProvider jwtTokenProvider;
    final AuthenticationManager authenticationManager;
    final UserRepository userRepository;
    final UserMapper userMapper;
    final AccessRepository accessRepository;

    public UserService(PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, UserRepository userRepository, UserMapper userMapper, AccessRepository accessRepository) {
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.accessRepository = accessRepository;
    }

    public BaseDTO login(String username, String password) {
        try {
            username = username.toLowerCase();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            Optional<User> user = userRepository.findUserByUsernameIgnoreCaseAndIsActiveIsTrue(username);
            if (!user.isPresent())
                throw ServiceException.builder()
                        .message(applicationProperties.getProperty("application.message.invalidLogin.text"))
                        .httpStatus(HttpStatus.UNAUTHORIZED).build();
            else
                return BaseDTO.builder().meta(MetaDTO.getInstance(applicationProperties)).data(jwtTokenProvider.createToken(username, user.get())).build();
        } catch (AuthenticationException e) {
            throw ServiceException.builder()
                    .message(applicationProperties.getProperty("application.message.invalidLogin.text"))
                    .httpStatus(HttpStatus.BAD_REQUEST).build();
        }
    }

    public BaseDTO addUser(AddNewUserBean addNewUserBean) {
        addNewUserBean.setUsername(addNewUserBean.getUsername().toLowerCase());
        if (!addNewUserBean.getPassword().equals(addNewUserBean.getConfirmPassword())) {
            throw ServiceException.builder()
                    .message(applicationProperties.getProperty("application.message.passwordNotMatched.text"))
                    .httpStatus(HttpStatus.BAD_REQUEST).build();
        }
        if (userRepository.existsUserByUsernameIgnoreCase(addNewUserBean.getUsername())) {
            throw ServiceException.builder()
                    .message(applicationProperties.getProperty("application.message.duplicateUser.text"))
                    .httpStatus(HttpStatus.BAD_REQUEST).build();
        }
        User user = userMapper.addNewUserBeanToUser(addNewUserBean);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAccesses(getUserAccesses(addNewUserBean.getAccessList()));

        userRepository.save(user);
        return BaseDTO.builder()
                .meta(MetaDTO.getInstance(applicationProperties))
                .data(userMapper.UserToUserDTO(user)).build();
    }

    public BaseDTO updateUser(UpdateUserBean updateUserBean) {
        User user = getUserEntity(updateUserBean.getId());
        user = userMapper.updateUserBeanToUser(user, updateUserBean);
        user.setAccesses(getUserAccesses(updateUserBean.getAccessList()));
        userRepository.save(user);
        return BaseDTO.builder()
                .meta(MetaDTO.getInstance(applicationProperties))
                .data(userMapper.UserToUserDTO(user)).build();
    }

    public BaseDTO deleteUser(long userId) {
        User user = getUserEntity(userId);
        user.setIsDeleted(true);
        userRepository.save(user);
        return BaseDTO.builder()
                .meta(MetaDTO.getInstance(applicationProperties))
                .data(userMapper.UserToUserDTO(user)).build();
    }

    public BaseDTO userResetPassword(ResetPasswordBean resetPasswordBean) {
        User user = getUserEntity(resetPasswordBean.getId());
        if (!resetPasswordBean.getPassword().equals(resetPasswordBean.getConfirmPassword())) {
            throw ServiceException.builder()
                    .message(applicationProperties.getProperty("application.message.passwordNotMatched.text"))
                    .httpStatus(HttpStatus.BAD_REQUEST).build();
        }
        user.setPassword(passwordEncoder.encode(resetPasswordBean.getPassword()));
        userRepository.save(user);
        return BaseDTO.builder()
                .meta(MetaDTO.getInstance(applicationProperties))
                .data(userMapper.UserToUserDTO(user)).build();
    }

    public BaseDTO userChangePassword(ChangePasswordBean changePasswordBean, HttpServletRequest request) {
        User user = getUserEntity(request);
        if (!passwordEncoder.matches(changePasswordBean.getOldPassword(), user.getPassword())) {
            throw ServiceException.builder()
                    .message(applicationProperties.getProperty("application.message.incorrectPassword.text"))
                    .httpStatus(HttpStatus.BAD_REQUEST).build();
        } else if (!changePasswordBean.getPassword().equals(changePasswordBean.getConfirmPassword())) {
            throw ServiceException.builder()
                    .message(applicationProperties.getProperty("application.message.passwordNotMatched.text"))
                    .httpStatus(HttpStatus.BAD_REQUEST).build();
        } else {
            user.setPassword(passwordEncoder.encode(changePasswordBean.getPassword()));
            userRepository.save(user);

            return BaseDTO.builder()
                    .meta(MetaDTO.getInstance(applicationProperties))
                    .data(userMapper.UserToUserDTO(user))
                    .build();
        }
    }

    public BaseDTO getAllUsers() {
        List<User> result = userRepository.findAllByIsActiveIsTrue();
        List<UserDTO> userDTOList = new ArrayList<>();
        result.forEach(user -> userDTOList.add(userMapper.UserToUserDTO(user)));
        return BaseDTO.builder()
                .meta(MetaDTO.getInstance(applicationProperties))
                .data(userDTOList).build();
    }

    public BaseDTO getUser(Long userId) {
        User user = getUserEntity(userId);
        user.setAccesses(user.getAccesses().stream().distinct().collect(Collectors.toList()));
        return BaseDTO.builder()
                .meta(MetaDTO.getInstance(applicationProperties))
                .data(userMapper.UserToUserDTO(user)).build();
    }

    public User getUserEntity(String username) {
        Optional<User> user = userRepository.findUserByUsernameIgnoreCase(username.toLowerCase());
        if (!user.isPresent()) {
            throw ServiceException.builder()
                    .message(applicationProperties.getProperty("application.message.notFoundRecord.text"))
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }
        return user.get();
    }

    public User getCurrentUser(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        String username = jwtTokenProvider.getUsername(token);
        return getUserEntity(username);
    }

    public BaseDTO getAllUsers(Integer pageNumber, Integer pageSize) {
        Page<User> result = userRepository.findAll(PageRequest.of(pageNumber, pageSize));
        List<UserDTO> userDTOList = new ArrayList<>();
        result.forEach(user -> userDTOList.add(userMapper.UserToUserDTO(user)));
        return BaseDTO.builder()
                .meta(MetaDTO.getInstance(applicationProperties))
                .data(PageDTO.builder()
                        .content(userDTOList)
                        .totalPage(result.getTotalPages())
                        .pageNumber(result.getNumber())
                        .totalElement(result.getTotalElements())
                        .build()).build();
    }

    private User getUserEntity(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw ServiceException.builder()
                    .message(applicationProperties.getProperty("application.message.notFoundRecord.text"))
                    .httpStatus(HttpStatus.NOT_FOUND).build();
        }
        return user.get();
    }

    private User getUserEntity(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        String username = jwtTokenProvider.getUsername(token);
        User user = getUserEntity(username);
        return user;
    }

    private List<Access> getUserAccesses(List<Long> listIds) {
        List<Access> accesses = null;
        if (listIds != null && !listIds.isEmpty()) {
            accesses = accessRepository.findAccessByIdIn(listIds);
        }
        return accesses;
    }
}