package com.scoinone.core.dto.common;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PageInfoDto {
    private Integer totalPages;
    private Long totalElements;
}