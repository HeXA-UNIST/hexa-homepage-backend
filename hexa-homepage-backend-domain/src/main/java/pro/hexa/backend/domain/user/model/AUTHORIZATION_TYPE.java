package pro.hexa.backend.domain.user.model;

import pro.hexa.backend.domain.model.model.EnumModel;

public enum AUTHORIZATION_TYPE implements EnumModel {
    Member("member"),
    Graduate("graduate"),
    Pro("pro"),
    Admin("admin");

    private final String value;

    AUTHORIZATION_TYPE(String value) {
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
