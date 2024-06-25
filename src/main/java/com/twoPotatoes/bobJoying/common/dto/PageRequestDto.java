package com.twoPotatoes.bobJoying.common.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class PageRequestDto {
    @Min(value = 1, message = "페이지는 1 이상이어야 합니다.")
    private int page;
    @Min(value = 1, message = "size는 1 이상이어야 합니다.")
    private int size;
    private String sortBy;
    private Boolean isAsc;
}
