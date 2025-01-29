package com.scoinone.order.mapper;

import com.scoinone.order.dto.response.point.CreatePointResponseDto;
import com.scoinone.order.dto.response.point.GetPointResponseDto;
import com.scoinone.order.entity.PointEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PointMapper {
    PointMapper INSTANCE = Mappers.getMapper(PointMapper.class);

    @Mapping(source = "balance", target = "balance")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    GetPointResponseDto pointToGetPointResponseDto(PointEntity point);

    @Mapping(source = "id", target = "pointId")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "balance", target = "balance")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    CreatePointResponseDto pointToCreatePointResponseDto(PointEntity point);
}
