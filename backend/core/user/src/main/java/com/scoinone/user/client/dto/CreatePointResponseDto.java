package com.scoinone.user.client.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePointResponseDto {
    private String pointId;
    private String userId;
    private BigDecimal balance;
    private String createdAt;
    private String updatedAt;
}
