package com.qin.module.user.converter;

import com.qin.module.user.dto.UserDto;
import com.qin.module.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserConverter {

    @Mappings({
            @Mapping(source = "id",target = "id"),
            @Mapping(source = "city",target = "city"),
            @Mapping(source = "name",target = "name")
    })
    UserDto user2Dto(User user);

    @Mappings({
            @Mapping(source = "id",target = "id"),
            @Mapping(source = "city",target = "city"),
            @Mapping(source = "name",target = "name")
    })
    User dto2User(UserDto dto);

    List<UserDto> list2List(List<User> list);
}
