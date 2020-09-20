package com.pyruz.rest.secured.model.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserChangePasswordRequest {
    @NotBlank
    @Size(min = 5,max = 20)
    private String oldPassword;
    @NotBlank
    @Size(min = 8,max = 20)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "application.message.passwordError.text")
    private String password;
    @NotBlank
    @Size(min = 8,max = 20)
    private String confirmPassword;
}