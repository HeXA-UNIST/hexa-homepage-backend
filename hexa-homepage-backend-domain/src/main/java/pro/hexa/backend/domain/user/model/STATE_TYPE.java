package pro.hexa.backend.domain.user.model;

import pro.hexa.backend.domain.model.model.EnumModel;

public enum STATE_TYPE implements EnumModel {
    재학("재학"),
    휴학("휴학"),
    졸업("졸업");

    private final String value;

    STATE_TYPE(String value) {
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
