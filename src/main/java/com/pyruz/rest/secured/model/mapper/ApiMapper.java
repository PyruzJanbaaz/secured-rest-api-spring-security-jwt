package com.pyruz.rest.secured.model.mapper;


import com.pyruz.rest.secured.model.dto.ApiDTO;
import com.pyruz.rest.secured.model.entity.Api;
import org.mapstruct.Mapper;

@Mapper
public interface ApiMapper {
    ApiDTO ApiToApiDTO(Api menu);
}
