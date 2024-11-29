package com.scoinone.core.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageInfoDto {
    private Integer totalPages;
    private Long totalElements;
}