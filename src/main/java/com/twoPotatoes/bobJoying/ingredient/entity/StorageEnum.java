package com.twoPotatoes.bobJoying.ingredient.entity;

import com.twoPotatoes.bobJoying.common.constants.MyIngredientConstants;

import lombok.Getter;

@Getter
public enum StorageEnum {
    FRIDGE(MyIngredientConstants.FRIDGE),
    FREEZER(MyIngredientConstants.FREEZER),
    ROOM_TEMPERATURE(MyIngredientConstants.ROOM_TEMPERATURE);

    private final String storage;

    StorageEnum(String storage) {
        this.storage = storage;
    }
}
