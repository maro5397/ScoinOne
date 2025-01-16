package com.scoinone.user.mapper;

import com.scoinone.user.dto.response.user.CreateUserResponseDto;
import com.scoinone.user.dto.response.user.GetUserResponseDto;
import com.scoinone.user.dto.response.user.UpdateUserResponseDto;
import com.scoinone.user.entity.UserEntity;
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
    CreateUserResponseDto userToCreateUserResponseDto(UserEntity user);

    @Mapping(source = "id", target = "userId")
    @Mapping(source = "customUsername", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    GetUserResponseDto userToGetUserResponseDto(UserEntity user);

    @Mapping(source = "id", target = "userId")
    @Mapping(source = "customUsername", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    UpdateUserResponseDto userToUpdateUserResponseDto(UserEntity user);
}