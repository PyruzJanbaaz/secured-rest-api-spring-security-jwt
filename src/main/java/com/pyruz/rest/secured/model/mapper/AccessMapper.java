package com.pyruz.rest.secured.model.mapper;

import com.pyruz.rest.secured.model.domain.AddNewAccessBean;
import com.pyruz.rest.secured.model.domain.UpdateAccessBean;
import com.pyruz.rest.secured.model.dto.AccessDTO;
import com.pyruz.rest.secured.model.entity.Access;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


@Mapper
public interface AccessMapper {
    AccessDTO accessToAccessDTO(Access access);
    Access addNewAccessBeanToAccess(AddNewAccessBean addNewAccessBean);
    Access updateAccessBeanToAccess(@MappingTarget Access access, UpdateAccessBean updateAccessBean);
}
