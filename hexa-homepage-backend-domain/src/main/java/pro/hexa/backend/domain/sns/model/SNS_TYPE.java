package pro.hexa.backend.domain.sns.model;

import pro.hexa.backend.domain.model.model.EnumModel;

public enum SNS_TYPE implements EnumModel {
    Facebook("facebook"),
    Kakaotalk("kakaotalk"),
    LinkedIn("Linked In"),
    ETC("etc")
    ;

    private final String value;

    SNS_TYPE(String value) {
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
