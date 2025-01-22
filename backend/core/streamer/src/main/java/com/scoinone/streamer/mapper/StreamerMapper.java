package com.scoinone.streamer.mapper;

import com.scoinone.streamer.dto.response.streamer.CreateStreamerResponseDto;
import com.scoinone.streamer.dto.response.streamer.GetStreamerResponseDto;
import com.scoinone.streamer.dto.response.streamer.GetStreamersResponseDto;
import com.scoinone.streamer.dto.response.streamer.UpdateStreamerResponseDto;
import com.scoinone.streamer.entity.StreamerEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StreamerMapper {
    StreamerMapper INSTANCE = Mappers.getMapper(StreamerMapper.class);

    @Mapping(source = "id", target = "streamerId")
    @Mapping(source = "virtualAsset.id", target = "virtualAssetId")
    @Mapping(source = "liveStreamingPlatform", target = "liveStreamingPlatform")
    @Mapping(source = "liveStreamingPlatformStreamerId", target = "liveStreamingPlatformStreamerId")
    @Mapping(source = "searchKeyword", target = "searchKeyword")
    @Mapping(source = "youtubeId", target = "youtubeId")
    @Mapping(source = "naverCafeId", target = "naverCafeId")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    GetStreamerResponseDto streamerToGetStreamerResponseDto(StreamerEntity streamer);

    @Mapping(source = "id", target = "streamerId")
    @Mapping(source = "liveStreamingPlatform", target = "liveStreamingPlatform")
    @Mapping(source = "liveStreamingPlatformStreamerId", target = "liveStreamingPlatformStreamerId")
    @Mapping(source = "searchKeyword", target = "searchKeyword")
    @Mapping(source = "youtubeId", target = "youtubeId")
    @Mapping(source = "naverCafeId", target = "naverCafeId")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(source = "virtualAsset.id", target = "virtualAsset.virtualAssetId")
    @Mapping(source = "virtualAsset.name", target = "virtualAsset.name")
    @Mapping(source = "virtualAsset.symbol", target = "virtualAsset.symbol")
    @Mapping(source = "virtualAsset.description", target = "virtualAsset.description")
    @Mapping(source = "virtualAsset.createdAt", target = "virtualAsset.createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    CreateStreamerResponseDto streamerToCreateStreamerResponseDto(StreamerEntity streamer);

    @Mapping(source = "id", target = "streamerId")
    @Mapping(source = "liveStreamingPlatform", target = "liveStreamingPlatform")
    @Mapping(source = "liveStreamingPlatformStreamerId", target = "liveStreamingPlatformStreamerId")
    @Mapping(source = "searchKeyword", target = "searchKeyword")
    @Mapping(source = "youtubeId", target = "youtubeId")
    @Mapping(source = "naverCafeId", target = "naverCafeId")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    UpdateStreamerResponseDto streamerToUpdateStreamerResponseDto(StreamerEntity streamer);

    List<GetStreamerResponseDto> streamersToGetStreamersResponseDto(List<StreamerEntity> streamers);

    default GetStreamersResponseDto listToGetStreamersResponseDto(List<StreamerEntity> streamers) {
        GetStreamersResponseDto responseDto = new GetStreamersResponseDto();
        responseDto.setStreamers(streamersToGetStreamersResponseDto(streamers));
        return responseDto;
    }
}
