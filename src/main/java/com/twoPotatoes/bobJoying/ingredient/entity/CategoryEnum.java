package com.twoPotatoes.bobJoying.ingredient.entity;

import com.twoPotatoes.bobJoying.common.constants.MyIngredientConstants;

import lombok.Getter;

@Getter
public enum CategoryEnum {
    FRUIT(MyIngredientConstants.FRUIT),
    VEGETABLE(MyIngredientConstants.VEGETABLE),
    MEAT(MyIngredientConstants.MEAT),
    MARINE_PRODUCT(MyIngredientConstants.MARINE_PRODUCT),
    DAIRY_PRODUCT(MyIngredientConstants.DAIRY_PRODUCT),
    PLATE(MyIngredientConstants.PLATE),
    DRINK(MyIngredientConstants.DRINK),
    ALCOHOL(MyIngredientConstants.ALCOHOL),
    SEASONING(MyIngredientConstants.SEASONING),
    SPICE(MyIngredientConstants.SPICE),
    BREAD(MyIngredientConstants.BREAD),
    DESSERT(MyIngredientConstants.DESSERT),
    NUT(MyIngredientConstants.NUT),
    GRAIN(MyIngredientConstants.GRAIN),
    ETC(MyIngredientConstants.ETC);

    private final String category;

    CategoryEnum(String category) {
        this.category = category;
    }
}
