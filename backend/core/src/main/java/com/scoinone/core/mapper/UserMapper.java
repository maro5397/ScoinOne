package com.scoinone.core.mapper;

import com.scoinone.core.dto.response.user.CreateUserResponseDto;
import com.scoinone.core.dto.response.user.GetUserResponseDto;
import com.scoinone.core.dto.response.user.UpdateUserResponseDto;
import com.scoinone.core.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "id", target = "userId")
    @Mapping(source = "customUsername", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    CreateUserResponseDto userToCreateUserResponseDto(User user);

    @Mapping(source = "id", target = "userId")
    @Mapping(source = "customUsername", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    GetUserResponseDto userToGetUserResponseDto(User user);

    @Mapping(source = "id", target = "userId")
    @Mapping(source = "customUsername", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    UpdateUserResponseDto userToUpdateUserResponseDto(User user);
}