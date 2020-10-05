package com.pyruz.rest.secured.controller;

import com.pyruz.rest.secured.model.domain.AddNewUserBean;
import com.pyruz.rest.secured.model.domain.ChangePasswordBean;
import com.pyruz.rest.secured.model.domain.ResetPasswordBean;
import com.pyruz.rest.secured.model.domain.UpdateUserBean;
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
@RequestMapping("api/")
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(value = "v1/user", name = "Add new user")
    public ResponseEntity<?> addUser(@Valid @RequestBody AddNewUserBean addNewUserBean) {
        return new ResponseEntity<>(userService.addUser(addNewUserBean), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping(value = "v1/user" , name = "Update a user")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserBean updateUserBean) {
        return new ResponseEntity<>(userService.updateUser(updateUserBean), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping(value = "v1/user", name = "Delete a user")
    public ResponseEntity<?> deleteUser(@ApiParam(value = "1", name = "id", required = true) @RequestParam Long id) {
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "v1/user", name = "Get user by id")
    public ResponseEntity<?> getUser(@ApiParam(value = "1", name = "id", required = true) @RequestParam Long id) {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "v1/users" , name = "Get users by pagination")
    public ResponseEntity<?> getUsers(@ApiParam(value = "0", name = "pageNumber", required = true) @RequestParam @Min(0)  Integer pageNumber,
                                      @ApiParam(value = "10", name = "pageSize", required = true) @RequestParam @Min(1) Integer pageSize) {
        return new ResponseEntity<>(userService.getAllUsers(pageNumber, pageSize), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping(value = "v1/user/reset-password" , name = "Reset password")
    public ResponseEntity<?> resetUserPassword(@Valid @RequestBody ResetPasswordBean resetPasswordBean) {
        return new ResponseEntity<>(userService.userResetPassword(resetPasswordBean), HttpStatus.OK);
    }

    @GetMapping(value = "v1/user/login" , name = "Login")
    public ResponseEntity login(
            @ApiParam(value = "admin", name = "username", required = true) @RequestParam @Size(min = 3,max = 50) String username,
            @ApiParam(value = "123456", name = "password", required = true) @RequestParam @Size(min = 3,max = 20) String password) {
        return new ResponseEntity(userService.login(username, password), HttpStatus.OK);
    }

    @PutMapping(value = "v1/user/change-password" , name = "Change password")
    public ResponseEntity<?> changeUserPassword(@Valid @RequestBody ChangePasswordBean changePasswordBean, HttpServletRequest request) {
        return new ResponseEntity<>(userService.userChangePassword(changePasswordBean,request), HttpStatus.OK);
    }

}
