package pro.hexa.backend.domain.user.model;

import pro.hexa.backend.domain.model.model.EnumModel;

public enum GENDER_TYPE implements EnumModel {
    남("male"),
    여("female"),
    기타("etc.")
    ;

    private final String value;

    GENDER_TYPE(String value) {
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
