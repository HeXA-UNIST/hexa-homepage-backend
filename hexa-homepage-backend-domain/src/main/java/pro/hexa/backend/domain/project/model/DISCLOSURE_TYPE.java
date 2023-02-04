package pro.hexa.backend.domain.project.model;

import pro.hexa.backend.domain.model.model.EnumModel;

public enum DISCLOSURE_TYPE implements EnumModel {
    공개("공개"),
    비공개("비공개");

    private final String value;

    DISCLOSURE_TYPE(String value) {
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
