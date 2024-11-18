package com.scoinone.core.dto.response.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserResponseDto {
    private Long userId;
    private String username;
    private String email;
    private String createdAt;
    private String updatedAt;
}