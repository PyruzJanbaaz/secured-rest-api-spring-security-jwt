package com.pyruz.rest.secured.model.mapper;

import com.pyruz.rest.secured.model.domain.UserAddRequest;
import com.pyruz.rest.secured.model.domain.UserUpdateRequest;
import com.pyruz.rest.secured.model.dto.UserDTO;
import com.pyruz.rest.secured.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UserMapper {
    UserDTO UserToUserDTO(User user);
    User UserAddRequestToUser(UserAddRequest userAddRequest);
    User UserUpdateRequestToUser(@MappingTarget User user, UserUpdateRequest userUpdateRequest);
}
