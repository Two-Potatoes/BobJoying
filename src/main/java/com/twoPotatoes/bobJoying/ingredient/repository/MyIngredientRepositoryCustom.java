package com.twoPotatoes.bobJoying.ingredient.repository;

import java.util.List;

import com.twoPotatoes.bobJoying.common.dto.PageRequestDto;
import com.twoPotatoes.bobJoying.ingredient.entity.CategoryEnum;
import com.twoPotatoes.bobJoying.ingredient.entity.MyIngredient;
import com.twoPotatoes.bobJoying.ingredient.entity.StorageEnum;

public interface MyIngredientRepositoryCustom {
    // 내 재료 Storage 별 조회
    List<MyIngredient> findAllByMemberAndStorage(
        int memberId, PageRequestDto pageDto, StorageEnum storageEnum
    );

    // 내 재료 Category 별 조회
    List<MyIngredient> findAllByMemberAndCategory(
        int memberId, PageRequestDto pageDto, CategoryEnum categoryEnum
    );

    // 내 재료 모두 조회
    List<MyIngredient> findAllByMember(
        int memberId, PageRequestDto pageDto
    );
}
