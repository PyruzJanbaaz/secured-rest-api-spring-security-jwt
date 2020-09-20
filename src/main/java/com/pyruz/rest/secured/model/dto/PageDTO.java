package com.pyruz.rest.secured.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO {
    private List content;
    private Integer pageNumber;
    private Integer totalPage;
    private Long totalElement;
}
