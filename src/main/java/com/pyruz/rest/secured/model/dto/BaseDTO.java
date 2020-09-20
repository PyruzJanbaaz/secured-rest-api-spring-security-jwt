package com.pyruz.rest.secured.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@Builder
public class BaseDTO<T> {

    private MetaDTO meta;

    private T data;

    public BaseDTO(MetaDTO meta, T data) {
        this.meta = meta;
        this.data = data;
    }

    public BaseDTO(MetaDTO meta) {
        this.meta = meta;
    }



}
