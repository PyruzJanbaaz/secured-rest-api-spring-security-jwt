package com.pyruz.rest.secured.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiDTO extends BaseEntityDTO<Long>{
    public String title;
    public String role;
    public String url;
}
