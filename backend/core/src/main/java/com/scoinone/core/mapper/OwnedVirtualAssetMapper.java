package com.scoinone.core.mapper;

import com.scoinone.core.dto.response.user.GetOwnedAssetsResponseDto;
import com.scoinone.core.dto.response.user.GetOwnedAssetResponseDto;
import com.scoinone.core.entity.OwnedVirtualAsset;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OwnedVirtualAssetMapper {
    OwnedVirtualAssetMapper INSTANCE = Mappers.getMapper(OwnedVirtualAssetMapper.class);

    @Mapping(source = "ownedVirtualAsset.id", target = "ownedVirtualAssetId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "virtualAsset.id", target = "virtualAssetId")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    GetOwnedAssetResponseDto ownedVirtualAssetToGetOwnedAssetResponseDto(OwnedVirtualAsset ownedVirtualAsset);

    List<GetOwnedAssetResponseDto> ownedVirtualAssetsToGetOwnedAssetsResponseDto(List<OwnedVirtualAsset> ownedVirtualAssets);

    default GetOwnedAssetsResponseDto listToGetOwnedAssetsResponseDto(List<OwnedVirtualAsset> ownedVirtualAssets) {
        GetOwnedAssetsResponseDto responseDto = new GetOwnedAssetsResponseDto();
        responseDto.setOwnedAssets(ownedVirtualAssetsToGetOwnedAssetsResponseDto(ownedVirtualAssets));
        return responseDto;
    }
}