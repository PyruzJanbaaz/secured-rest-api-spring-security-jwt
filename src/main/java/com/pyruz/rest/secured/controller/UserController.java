package com.pyruz.rest.secured.controller;

import com.pyruz.rest.secured.model.domain.UserAddRequest;
import com.pyruz.rest.secured.model.domain.UserChangePasswordRequest;
import com.pyruz.rest.secured.model.domain.UserResetPasswordRequest;
import com.pyruz.rest.secured.model.domain.UserUpdateRequest;
import com.pyruz.rest.secured.service.UserService;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@RestController
@Validated
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(value = "v1/user")
    public ResponseEntity<?> addUser(@Valid @RequestBody UserAddRequest userAddRequest) {
        return new ResponseEntity<>(userService.addUser(userAddRequest), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping(value = "v1/user")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        return new ResponseEntity<>(userService.updateUser(userUpdateRequest), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping(value = "v1/user")
    public ResponseEntity<?> deleteUser(@ApiParam(value = "1", name = "id", required = true) @RequestParam Long id) {
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "v1/user")
    public ResponseEntity<?> getUser(@ApiParam(value = "1", name = "id", required = true) @RequestParam Long id) {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "v1/users")
    public ResponseEntity<?> getUsers(@ApiParam(value = "0", name = "pageNumber", required = true) @RequestParam @Min(0)  Integer pageNumber,
                                      @ApiParam(value = "10", name = "pageSize", required = true) @RequestParam @Min(1) Integer pageSize) {
        return new ResponseEntity<>(userService.getAllUsers(pageNumber, pageSize), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping(value = "v1/user/reset-password")
    public ResponseEntity<?> resetUserPassword(@Valid @RequestBody UserResetPasswordRequest userResetPasswordRequest) {
        return new ResponseEntity<>(userService.userResetPassword(userResetPasswordRequest), HttpStatus.OK);
    }

    @GetMapping("v1/user/login")
    public ResponseEntity login(
            @ApiParam(value = "admin", name = "username", required = true) @RequestParam @Size(min = 3,max = 50) String username,
            @ApiParam(value = "123456", name = "password", required = true) @RequestParam @Size(min = 8,max = 20) String password) {
        return new ResponseEntity(userService.login(username, password), HttpStatus.OK);
    }

    @PutMapping(value = "v1/user/change-password")
    public ResponseEntity<?> changeUserPassword(@Valid @RequestBody UserChangePasswordRequest userChangePasswordRequest, HttpServletRequest request) {
        return new ResponseEntity<>(userService.userChangePassword(userChangePasswordRequest,request), HttpStatus.OK);
    }

}
