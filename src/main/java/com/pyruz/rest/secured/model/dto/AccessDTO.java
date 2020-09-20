package com.pyruz.rest.secured.model.dto;


import lombok.*;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class AccessDTO extends BaseEntityDTO<Long> {
    private String title;

}