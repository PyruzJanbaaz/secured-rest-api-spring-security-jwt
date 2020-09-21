package com.pyruz.rest.secured.model.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddNewUserBean {
    @NotBlank @Size(min = 3,max = 50)
    @Pattern(regexp = "^[0-9a-zA-Z\\-_.@]{3,50}$", message = "application.message.usernameError.text")
    private String username;
    @NotBlank @Size(min = 8,max = 20)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$", message = "application.message.passwordError.text")
    private String password;
    @NotBlank @Size(min = 8,max = 20)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$", message = "application.message.passwordError.text")
    private String confirmPassword;
    @NotBlank @Size(max = 250)
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    private String email;
    @NotBlank @Size(max = 50)
    private String firstName;
    @NotBlank @Size(max = 50)
    private String lastName;
    @NotNull
    private Boolean isActive;
    private List<Long> accessList;
}