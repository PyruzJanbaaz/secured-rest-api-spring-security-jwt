package com.pyruz.rest.secured.model.mapper;

import com.pyruz.rest.secured.model.domain.AccessAddRequest;
import com.pyruz.rest.secured.model.domain.AccessUpdateRequest;
import com.pyruz.rest.secured.model.dto.AccessDTO;
import com.pyruz.rest.secured.model.entity.Access;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


@Mapper
public interface AccessMapper {
    AccessDTO accessToAccessDTO(Access access);
    Access accessAddRequestToAccess(AccessAddRequest accessAddRequest);
    Access accessUpdateRequestToAccess(@MappingTarget Access access, AccessUpdateRequest accessUpdateRequest);
}
