package com.scoinone.core.dto.request.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequestDto {
    private String username;
    private String password;
    private String email;
}