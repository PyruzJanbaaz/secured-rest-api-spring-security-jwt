package com.pyruz.rest.secured.model.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccessUpdateRequest {
    @NotNull
    private Long id;
    @NotBlank @Size(min = 3,max = 50)
    private String title;
    @NotNull
    private Boolean isActive;
    private List<Long> apiIds;
}