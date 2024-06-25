package com.twoPotatoes.bobJoying.ingredient.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.twoPotatoes.bobJoying.common.dto.PageRequestDto;
import com.twoPotatoes.bobJoying.common.exception.CustomErrorCode;
import com.twoPotatoes.bobJoying.common.exception.CustomException;
import com.twoPotatoes.bobJoying.ingredient.entity.CategoryEnum;
import com.twoPotatoes.bobJoying.ingredient.entity.MyIngredient;
import com.twoPotatoes.bobJoying.ingredient.entity.QIngredient;
import com.twoPotatoes.bobJoying.ingredient.entity.QMyIngredient;
import com.twoPotatoes.bobJoying.ingredient.entity.StorageEnum;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MyIngredientRepositoryImpl implements MyIngredientRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    QMyIngredient myIngredient = QMyIngredient.myIngredient;
    QIngredient ingredient = QIngredient.ingredient;

    @Override
    public List<MyIngredient> findAllByMemberAndStorage(
        int memberId, PageRequestDto pageDto, StorageEnum storageEnum) {

        OrderSpecifier<?> orderSpecifier = getMyIngredientOrderSpecifier(pageDto);

        return jpaQueryFactory.selectFrom(myIngredient)
            .join(myIngredient.ingredient, ingredient).fetchJoin()
            .where(myIngredient.member.id.eq(memberId))
            .where(myIngredient.storage.eq(storageEnum))
            .orderBy(orderSpecifier)
            .offset((long)pageDto.getPage() * pageDto.getSize())
            .limit(pageDto.getSize())
            .fetch();
    }

    @Override
    public List<MyIngredient> findAllByMemberAndCategory(
        int memberId, PageRequestDto pageDto, CategoryEnum categoryEnum) {

        OrderSpecifier<?> orderSpecifier = getMyIngredientOrderSpecifier(pageDto);

        return jpaQueryFactory.selectFrom(myIngredient)
            .join(myIngredient.ingredient, ingredient).fetchJoin()
            .where(myIngredient.member.id.eq(memberId))
            .where(myIngredient.ingredient.category.eq(categoryEnum))
            .orderBy(orderSpecifier)
            .offset((long)pageDto.getPage() * pageDto.getSize())
            .limit(pageDto.getSize())
            .fetch();
    }

    @Override
    public List<MyIngredient> findAllByMember(
        int memberId, PageRequestDto pageDto) {

        OrderSpecifier<?> orderSpecifier = getMyIngredientOrderSpecifier(pageDto);

        return jpaQueryFactory.selectFrom(myIngredient)
            .join(myIngredient.ingredient, ingredient).fetchJoin()
            .where(myIngredient.member.id.eq(memberId))
            .orderBy(orderSpecifier)
            .offset((long)pageDto.getPage() * pageDto.getSize())
            .limit(pageDto.getSize())
            .fetch();
    }

    private OrderSpecifier<?> getMyIngredientOrderSpecifier(PageRequestDto pageDto) {
        boolean isAsc = pageDto.getIsAsc();
        OrderSpecifier<?> orderSpecifier = null;
        switch (pageDto.getSortBy()) {
            case "name":
                if (isAsc) {
                    orderSpecifier = myIngredient.ingredient.name.asc();
                } else {
                    orderSpecifier = myIngredient.ingredient.name.desc();
                }
                break;
            case "expirationDate":
                if (isAsc) {
                    orderSpecifier = myIngredient.expirationDate.asc().nullsLast();
                } else {
                    orderSpecifier = myIngredient.expirationDate.desc().nullsLast();
                }
                break;
            case "id":
                if (isAsc) {
                    orderSpecifier = myIngredient.id.asc();
                } else {
                    orderSpecifier = myIngredient.id.desc();
                }
                break;
            default:
                throw new CustomException(CustomErrorCode.INVALID_SORTBY);
        }
        return orderSpecifier;
    }
}
