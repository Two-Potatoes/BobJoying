package com.twoPotatoes.bobJoying.ingredient.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.twoPotatoes.bobJoying.common.dto.PageRequestDto;
import com.twoPotatoes.bobJoying.common.exception.CustomErrorCode;
import com.twoPotatoes.bobJoying.common.exception.CustomException;
import com.twoPotatoes.bobJoying.ingredient.entity.CategoryEnum;
import com.twoPotatoes.bobJoying.ingredient.entity.Ingredient;
import com.twoPotatoes.bobJoying.ingredient.entity.QIngredient;
import com.twoPotatoes.bobJoying.ingredient.entity.StorageEnum;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class IngredientRepositoryImpl implements IngredientRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    QIngredient ingredient = QIngredient.ingredient;

    @Override
    public List<Ingredient> findAllByCategory(PageRequestDto pageDto, CategoryEnum category) {
        OrderSpecifier<?> orderSpecifier = getIngredientOrderSpecifier(pageDto);
        return jpaQueryFactory.selectFrom(ingredient)
            .where(ingredient.category.eq(category))
            .orderBy(orderSpecifier)
            .offset((long)pageDto.getPage() * pageDto.getSize())
            .limit(pageDto.getSize())
            .fetch();
    }

    @Override
    public List<Ingredient> findAllByStorage(PageRequestDto pageDto, StorageEnum storage) {
        return List.of();
    }

    @Override
    public List<Ingredient> findAll(PageRequestDto pageDto) {
        return List.of();
    }

    private OrderSpecifier<?> getIngredientOrderSpecifier(PageRequestDto pageDto) {
        boolean isAsc = pageDto.getIsAsc();
        OrderSpecifier<?> orderSpecifier = null;
        switch (pageDto.getSortBy()) {
            case "name":
                if (isAsc) {
                    orderSpecifier = ingredient.name.asc();
                } else {
                    orderSpecifier = ingredient.name.desc();
                }
                break;
            case "id":
                if (isAsc) {
                    orderSpecifier = ingredient.id.asc();
                } else {
                    orderSpecifier = ingredient.id.desc();
                }
                break;
            default:
                throw new CustomException(CustomErrorCode.INVALID_SORTBY);
        }
        return orderSpecifier;
    }
}
