package com.pyruz.rest.secured.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccessAddRequest {
    @NotBlank @Size(min = 3, max = 30)
    private String title;
    private List<Long> menuList;
}
