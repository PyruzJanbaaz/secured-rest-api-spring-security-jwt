package com.pyruz.rest.secured.model.mapper;

import com.pyruz.rest.secured.model.domain.AddNewUserBean;
import com.pyruz.rest.secured.model.domain.UpdateUserBean;
import com.pyruz.rest.secured.model.dto.UserDTO;
import com.pyruz.rest.secured.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UserMapper {
    UserDTO UserToUserDTO(User user);
    User addNewUserBeanToUser(AddNewUserBean addNewUserBean);
    User updateUserBeanToUser(@MappingTarget User user, UpdateUserBean updateUserBean);
}
