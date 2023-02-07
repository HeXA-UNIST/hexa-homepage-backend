package pro.hexa.backend.domain.Board.model;

import pro.hexa.backend.domain.model.model.EnumModel;

public enum BOARD_TYPE implements EnumModel {
    공지("공지"),
    수상("수상");

    private String value;

    BOARD_TYPE(String value) {
        this.value = value;
    }

    @Override
    public String getKey() {
        return this.name();
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
