package com.pyruz.rest.secured.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO extends BaseEntityDTO<Long>
{
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private List<AccessDTO> accesses;
}
