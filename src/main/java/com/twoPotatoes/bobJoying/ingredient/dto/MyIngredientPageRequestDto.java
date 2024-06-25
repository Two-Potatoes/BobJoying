package com.twoPotatoes.bobJoying.ingredient.dto;

import com.twoPotatoes.bobJoying.common.dto.PageRequestDto;
import com.twoPotatoes.bobJoying.ingredient.entity.CategoryEnum;
import com.twoPotatoes.bobJoying.ingredient.entity.StorageEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class MyIngredientPageRequestDto extends PageRequestDto {
    private CategoryEnum categoryEnum;
    private StorageEnum storageEnum;

    public PageRequestDto toPageRequestDto() {
        return PageRequestDto.builder()
            .page(this.getPage())
            .size(this.getSize())
            .sortBy(this.getSortBy())
            .isAsc(this.getIsAsc())
            .build();
    }
}
