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
public class UpdateUserBean {
    @NotNull
    private Long id;
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