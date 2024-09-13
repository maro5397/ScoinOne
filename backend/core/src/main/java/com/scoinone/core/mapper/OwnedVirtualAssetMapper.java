package com.scoinone.core.mapper;

import com.scoinone.core.dto.response.user.GetOwnedAssetListResponseDto;
import com.scoinone.core.dto.response.user.GetOwnedAssetResponseDto;
import com.scoinone.core.entity.OwnedVirtualAsset;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OwnedVirtualAssetMapper {

    @Mapping(source = "ownedVirtualAssetId", target = "ownedVirtualAssetId")
    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "virtualAsset.virtualAssetId", target = "virtualAssetId")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    GetOwnedAssetResponseDto ownedVirtualAssetToGetOwnedAssetResponseDto(OwnedVirtualAsset ownedVirtualAsset);

    List<GetOwnedAssetResponseDto> ownedVirtualAssetsToGetOwnedAssetResponseDtos(List<OwnedVirtualAsset> ownedVirtualAssets);

    default GetOwnedAssetListResponseDto listToGetOwnedAssetListResponseDto(List<OwnedVirtualAsset> ownedVirtualAssets) {
        GetOwnedAssetListResponseDto responseDto = new GetOwnedAssetListResponseDto();
        responseDto.setOwnedAssets(ownedVirtualAssetsToGetOwnedAssetResponseDtos(ownedVirtualAssets));
        return responseDto;
    }
}