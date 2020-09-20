package com.pyruz.rest.secured.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseEntityDTO<T> {
    protected T id;
    protected Timestamp createDate;
    protected Timestamp updateDate;
    protected Boolean isActive;
}