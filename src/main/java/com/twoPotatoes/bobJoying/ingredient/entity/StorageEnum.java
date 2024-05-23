package com.twoPotatoes.bobJoying.ingredient.entity;

import lombok.Getter;

@Getter
public enum StorageEnum {
    FRIDGE("냉장"),
    FREEZER("냉동"),
    ROOM_TEMPERATURE("실온");

    private final String storage;

    StorageEnum(String storage) {
        this.storage = storage;
    }
}
