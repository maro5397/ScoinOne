package com.scoinone.order.mapper;

import com.scoinone.order.dto.response.ownedvirtualasset.GetOwnedAssetResponseDto;
import com.scoinone.order.entity.OwnedVirtualAssetEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OwnedVirtualAssetMapper {
    OwnedVirtualAssetMapper INSTANCE = Mappers.getMapper(OwnedVirtualAssetMapper.class);

    @Mapping(source = "id", target = "ownedVirtualAssetId")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "virtualAssetId", target = "virtualAssetId")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    GetOwnedAssetResponseDto ownedVirtualAssetToGetOwnedAssetResponseDto(OwnedVirtualAssetEntity ownedVirtualAsset);

    List<GetOwnedAssetResponseDto> ownedVirtualAssetsToGetOwnedAssetsResponseDto(
            List<OwnedVirtualAssetEntity> ownedVirtualAssets
    );
}
