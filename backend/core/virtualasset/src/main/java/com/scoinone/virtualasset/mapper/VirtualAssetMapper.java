package com.scoinone.virtualasset.mapper;

import com.scoinone.virtualasset.dto.response.virtualasset.CreateVirtualAssetResponseDto;
import com.scoinone.virtualasset.dto.response.virtualasset.GetVirtualAssetResponseDto;
import com.scoinone.virtualasset.dto.response.virtualasset.GetVirtualAssetsResponseDto;
import com.scoinone.virtualasset.dto.response.virtualasset.UpdateVirtualAssetResponseDto;
import com.scoinone.virtualasset.entity.VirtualAssetEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VirtualAssetMapper {
    VirtualAssetMapper INSTANCE = Mappers.getMapper(VirtualAssetMapper.class);

    @Mapping(source = "id", target = "virtualAssetId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "symbol", target = "symbol")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    GetVirtualAssetResponseDto virtualAssetToGetVirtualAssetResponseDto(VirtualAssetEntity virtualAsset);

    @Mapping(source = "id", target = "virtualAssetId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "symbol", target = "symbol")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    CreateVirtualAssetResponseDto virtualAssetToCreateVirtualAssetResponseDto(VirtualAssetEntity virtualAsset);

    @Mapping(source = "id", target = "virtualAssetId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "symbol", target = "symbol")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    UpdateVirtualAssetResponseDto virtualAssetToUpdateVirtualAssetResponseDto(VirtualAssetEntity virtualAsset);

    List<GetVirtualAssetResponseDto> virtualAssetsToGetVirtualAssetsResponseDto(List<VirtualAssetEntity> virtualAssets);

    default GetVirtualAssetsResponseDto listToGetVirtualAssetsResponseDto(List<VirtualAssetEntity> virtualAssets) {
        GetVirtualAssetsResponseDto responseDto = new GetVirtualAssetsResponseDto();
        responseDto.setVirtualAssets(virtualAssetsToGetVirtualAssetsResponseDto(virtualAssets));
        return responseDto;
    }
}
