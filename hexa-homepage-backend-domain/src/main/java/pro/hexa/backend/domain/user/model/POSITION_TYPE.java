package pro.hexa.backend.domain.user.model;

import pro.hexa.backend.domain.model.model.EnumModel;

public enum POSITION_TYPE implements EnumModel {
    ;

    private final String value;

    POSITION_TYPE(String value) {
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
