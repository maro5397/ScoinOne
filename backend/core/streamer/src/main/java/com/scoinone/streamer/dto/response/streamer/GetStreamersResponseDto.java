package com.scoinone.streamer.dto.response.streamer;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetStreamersResponseDto {
    private List<GetStreamerResponseDto> streamers;
}
