package com.scoinone.core.mapper;

import com.scoinone.core.dto.response.virtualasset.CreateVirtualAssetResponseDto;
import com.scoinone.core.dto.response.virtualasset.GetVirtualAssetListResponseDto;
import com.scoinone.core.dto.response.virtualasset.GetVirtualAssetResponseDto;
import com.scoinone.core.dto.response.virtualasset.UpdateVirtualAssetResponseDto;
import com.scoinone.core.entity.VirtualAsset;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VirtualAssetMapper {
    VirtualAssetMapper INSTANCE = Mappers.getMapper(VirtualAssetMapper.class);

    @Mapping(source = "id", target = "virtualAssetId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "symbol", target = "symbol")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    GetVirtualAssetResponseDto virtualAssetToGetVirtualAssetResponseDto(VirtualAsset virtualAsset);

    @Mapping(source = "id", target = "virtualAssetId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "symbol", target = "symbol")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    CreateVirtualAssetResponseDto virtualAssetToCreateVirtualAssetResponseDto(VirtualAsset virtualAsset);

    @Mapping(source = "id", target = "virtualAssetId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "symbol", target = "symbol")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    UpdateVirtualAssetResponseDto virtualAssetToUpdateVirtualAssetResponseDto(VirtualAsset virtualAsset);

    List<GetVirtualAssetResponseDto> virtualAssetsToGetVirtualAssetsResponseDto(List<VirtualAsset> virtualAssets);

    default GetVirtualAssetListResponseDto listToGetVirtualAssetListResponseDto(List<VirtualAsset> virtualAssets) {
        GetVirtualAssetListResponseDto responseDto = new GetVirtualAssetListResponseDto();
        responseDto.setVirtualAssets(virtualAssetsToGetVirtualAssetsResponseDto(virtualAssets));
        return responseDto;
    }
}
